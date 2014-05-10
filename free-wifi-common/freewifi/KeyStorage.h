#ifndef KEYSTORAGE_H
#define KEYSTORAGE_H
#include <string>
#include "crypto/Data.h"

class KeyStorage
{
public:

    struct DecodeKey
    {
        DecodeKey();
        DataPtr key;
        DataPtr iv;
    };

    /**
     * @brief Return cryptographicaly secured
     * ID of wifi based on ssid and bssid
     * @param ssid human readable wifi name
     * @param bssid mac adress of wifi
     * @return
     */
    static std::string getWifiID(
            const std::string& ssid,
            const std::string& bssid);

    /**
     * @brief Returns the cryptographic key and iv
     * for decoding the info about given wifi
     * @param ssid human readable wifi name
     * @param bssid mac adress of wifi
     * @return
     */
    static DecodeKey getWifiDecodeKey(
            const std::string& ssid,
            const std::string& bssid);
};

#endif // KEYSTORAGE_H
