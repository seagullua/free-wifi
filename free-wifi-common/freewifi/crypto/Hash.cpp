#include "Hash.h"
#include <tomcrypt.h>
#include <mutex>
#include <random>
#include <freewifi/platform/Log.h>

namespace HashImpl
{
    static int _hash_id = -1;
    static int _hash_short = -1;
    static bool _inited = false;
    static std::mutex _hash_mutex;

    void initHash()
    {
        if(!_inited)
        {
            std::lock_guard<std::mutex> lock(_hash_mutex);
            if(!_inited)
            {
                _hash_id = register_hash(&sha256_desc);
                _hash_short = register_hash(&sha1_desc);
                if(_hash_id >= 0 && _hash_short >= 0)
                {
                    _inited = true;
                }
                else
                {
                    log("[Hash] init failed");
                }
            }
        }
    }

    DataPtr applyHash(const DataPtr& d, int hash_id)
    {
        if(_inited)
        {
            std::lock_guard<std::mutex> lock(_hash_mutex);

            hash_state sha;
            if(hash_descriptor[hash_id].init(&sha) == CRYPT_OK)
            {
                Data::Size hash_size = hash_descriptor[hash_id].hashsize;
                DataPtr res = Data::create();
                Data::ByteArr& raw = res->getRawData();
                raw.resize(hash_size);

                Data::ByteArr& input_raw = d->getRawData();

                int err = CRYPT_OK;
                const char empty[] = "";
                if(d->getSize() == 0)
                    err = hash_descriptor[hash_id].process(&sha, (const unsigned char*)empty, 0);
                else
                    err = hash_descriptor[hash_id].process(&sha, &input_raw[0], d->getSize());

                if(err == CRYPT_OK)
                {
                    if(hash_descriptor[hash_id].done(&sha, &raw[0]) == CRYPT_OK)
                    {
                        return res;
                    }
                }

            }
        }
        return nullptr;
    }
}
using namespace HashImpl;

DataPtr Hash::apply(const DataPtr& d)
{
    initHash();
    return applyHash(d, _hash_id);
}

DataPtr Hash::applyShort(const DataPtr& d)
{
    initHash();
    return applyHash(d, _hash_short);
}
