#include "KeyStorage.h"

#include "crypto/Hash.h"
#include "crypto/Base64.h"
#include "crypto/Protect.h"
#include "platform/Log.h"

KeyStorage::DecodeKey::DecodeKey()
    : key(nullptr),
      iv(nullptr)
{}

std::string KeyStorage::getWifiID(
        const std::string& ssid,
        const std::string& bssid)
{
    static Data salt1("9m3MzVV4Y9XGjEwWy3y9");
    static Data salt2("wF4mgVrxr6jKmKYDXfRx");
    static Data salt3("gyKub2sY7SujXDYAe6KV");

    DataPtr res = Data::create(salt1);
    res += ssid;
    res += salt2;
    res += bssid;
    res += salt3;

    //log("Before hash: "+res->toString());

    DataPtr hash = Hash::applyShort(res);
    if(hash)
    {
        DataPtr base64 = Base64::encode(hash);
        if(base64)
            return base64->toString();
    }
    return "";
}

KeyStorage::DecodeKey KeyStorage::getWifiDecodeKey(
        const std::string& ssid,
        const std::string& bssid)
{
    DecodeKey result;

    static Data salt1("MCeGNFxMgyswwJfn6zCf");
    static Data salt2("MfcLVmEF95tF6Sa6627e");
    static Data salt3("5aU3a9EmyuwyyTRss7wM");

    DataPtr res = Data::create(salt1);
    res += ssid;
    res += salt2;
    res += bssid;
    res += salt3;

    DataPtr hash = Hash::apply(res);
    if(hash)
    {
        Data::ByteArr& raw = hash->getRawData();
        if(raw.size() >= Protect::KEY_SIZE + Protect::IV_SIZE)
        {
            DataPtr key = Data::create(&raw[0], Protect::KEY_SIZE);
            DataPtr iv = Data::create(&raw[0]+Protect::KEY_SIZE, Protect::IV_SIZE);

            result.key = key;
            result.iv = iv;
        }
    }

    return result;
}
