#include "Protect.h"
#include "Random.h"
#include <cassert>
#include <mutex>
#include <tomcrypt.h>
#include "freewifi/platform/Log.h"


namespace ProtectImpl{
static std::mutex _protect_mutex;
static bool _is_inited = false;
static int _encryption_key = -1;


void initEncryption()
{
    if(!_is_inited)
    {
        std::lock_guard<std::mutex> lock(_protect_mutex);

        if(!_is_inited)
        {
            _encryption_key = register_cipher(&aes_desc);
            if(_encryption_key >= 0)
            {
                _is_inited = true;
            }
            else
            {
                log("[Protect] init failed");
            }
        }
    }
}

/**
 * @brief applies PKCS7
 * @param arr
 */
void applyPadding(Data::ByteArr& arr, const Data::Size padding_size)
{
    Data::Size append = padding_size - (arr.size() % padding_size);
    arr.resize(arr.size() + append, static_cast<Data::Byte>(append));
}

bool removePadding(Data::ByteArr& arr, const Data::Size padding_size)
{
    bool res = false;
    if(arr.size() > 0)
    {
        Data::Size padded = arr[arr.size()-1];
        if(padded <= padding_size && padded <= arr.size())
        {
            arr.resize(arr.size() - padded);
            res = true;
        }
    }
    return res;
}

bool performEncode(const Data::Byte* plain_text,
                   Data::Byte* cipher_text,
                   const Data::Size plain_text_size,
                   const Data::Byte* key,
                   const Data::Size key_size,
                   const Data::Byte* iv,
                   const Data::Size iv_size
                   )
{
    assert(key_size == iv_size);
    initEncryption();


    std::lock_guard<std::mutex> lock(_protect_mutex);
    if(_is_inited)
    {
        symmetric_CBC cbc;

        int err = CRYPT_OK;
        err = cbc_start(_encryption_key,
                        iv,
                        key,
                        key_size,
                        0,
                        &cbc);

        if(err == CRYPT_OK)
        {
            err = cbc_encrypt(plain_text, cipher_text, plain_text_size, &cbc);
            if(err == CRYPT_OK)
                return true;
        }


    }
    return false;
}

DataPtr performDecode(const Data::Byte* cipher_text,
                      const Data::Size cipher_text_size,
                      const Data::Byte* key,
                      const Data::Size key_size,
                      const Data::Byte* iv,
                      const Data::Size iv_size)
{
    initEncryption();
    assert(key_size == iv_size);

    bool ok = false;
    DataPtr res = Data::create();
    Data::ByteArr& raw = res->getRawData();
    raw.resize(cipher_text_size);

    std::lock_guard<std::mutex> lock(_protect_mutex);
    if(_is_inited && cipher_text_size > 0)
    {
        symmetric_CBC cbc;

        int err = CRYPT_OK;
        err = cbc_start(_encryption_key,
                        iv,
                        key,
                        key_size,
                        0,
                        &cbc);

        if(err == CRYPT_OK)
        {
            err = cbc_decrypt(cipher_text, &raw[0], cipher_text_size, &cbc);
            if(err == CRYPT_OK)
            {
                if(removePadding(raw, Protect::BLOCK_SIZE))
                {
                    ok = true;
                }
            }
        }
    }

    if(ok)
        return res;
    return nullptr;
}
}

using namespace ProtectImpl;

DataPtr Protect::encode(const DataPtr& plain_text, const DataPtr& key)
{
    assert(key->getSize() == KEY_SIZE);
    Data::ByteArr plain = plain_text->getRawData();
    applyPadding(plain, BLOCK_SIZE);

    DataPtr iv = Random::generateRandomData(IV_SIZE);
    Data::ByteArr& cipher_raw = iv->getRawData();
    cipher_raw.resize(IV_SIZE + plain.size());

    Data::ByteArr& key_raw = key->getRawData();

    if(performEncode(&plain[0], //plain text
                     &cipher_raw[0]+IV_SIZE, //cipher text
                     plain.size(), //length
                     &key_raw[0], KEY_SIZE, //key
                     &cipher_raw[0], IV_SIZE))
    {
        return iv;
    }
    return nullptr;
}

DataPtr Protect::encodeIV(const DataPtr& plain_text,
                          const DataPtr& key,
                          const DataPtr& iv)
{
    assert(key->getSize() == KEY_SIZE && iv->getSize() == IV_SIZE);
    Data::ByteArr plain = plain_text->getRawData();
    applyPadding(plain, BLOCK_SIZE);

    assert(plain.size() % BLOCK_SIZE == 0);

    Data::ByteArr& iv_raw = iv->getRawData();

    DataPtr cipher = Data::create();
    Data::ByteArr& cipher_raw = cipher->getRawData();

    cipher_raw.resize(plain.size());

    Data::ByteArr& key_raw = key->getRawData();

    if(performEncode(&plain[0], //plain text
                     &cipher_raw[0], //cipher text
                     plain.size(), //length
                     &key_raw[0], KEY_SIZE, //key
                     &iv_raw[0], IV_SIZE))
    {
        return cipher;
    }
    return nullptr;
}


DataPtr Protect::decode(const DataPtr& cipher_text, const DataPtr& key)
{
    assert(key->getSize() == KEY_SIZE);

    Data::Size plain_size = cipher_text->getSize() - IV_SIZE;

    if(plain_size % BLOCK_SIZE != 0 || plain_size == 0)
        return nullptr;

    Data::ByteArr& cipher_raw = cipher_text->getRawData();
    Data::ByteArr& key_raw = key->getRawData();

    return performDecode(&cipher_raw[0] + IV_SIZE, plain_size,
            &key_raw[0], KEY_SIZE,
            &cipher_raw[0], IV_SIZE);

}


DataPtr Protect::decodeIV(const DataPtr& cipher_text,
                          const DataPtr& key,
                          const DataPtr& iv)
{
    assert(key->getSize() == KEY_SIZE && iv->getSize() == IV_SIZE);

    Data::Size plain_size = cipher_text->getSize();

    if(plain_size % BLOCK_SIZE != 0 || plain_size == 0)
        return nullptr;

    Data::ByteArr& cipher_raw = cipher_text->getRawData();
    Data::ByteArr& key_raw = key->getRawData();
    Data::ByteArr& iv_raw = iv->getRawData();

    return performDecode(&cipher_raw[0], plain_size,
            &key_raw[0], KEY_SIZE,
            &iv_raw[0], IV_SIZE);
}


