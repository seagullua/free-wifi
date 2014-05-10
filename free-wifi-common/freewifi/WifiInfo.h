#ifndef WIFIINFO_H
#define WIFIINFO_H
#include <string>
#include <vector>
#include <memory>
#include "crypto/Data.h"

/**
 * @brief The level of wifi.
 * May have values [0-99]
 */

typedef int WifiLevel;

/**
 * @brief Stores the info about location of the wifi location
 * It may have info about latitude longitude and level of wifi in this coordinates
 */
class WifiLocation
{
public:
    WifiLocation();
    WifiLocation(double lat, double lon, const WifiLevel level);

    /**
     * @brief Returns true if there is info about wifi location
     * @return
     */
    bool isLocationDefined() const
    {
        return _is_defined;
    }

    /**
     * @brief Returns the latitude of place where the wifi was observed
     * @return
     */
    double getLatitude() const
    {
        return _lat;
    }

    /**
     * @brief Returns the longitude of place where the wifi was observed
     * @return
     */
    double getLongitude() const
    {
        return _lon;
    }

    /**
     * @brief Returns the level of wifi where it was observed
     * @return
     */
    WifiLevel getLevel() const
    {
        return _level;
    }
private:
    bool _is_defined;
    double _lat;
    double _lon;
    WifiLevel _level;
};

/**
 * @brief Info about password for wifi
 */
class WifiPassword
{
public:
    /**
     * @brief Creates object without password
     */
    WifiPassword();

    /**
     * @brief Creates object with password
     * @param password
     */
    WifiPassword(std::string password);

    /**
     * @brief Returns whether password exists
     * @return
     */
    bool hasPassword() const
    {
        return _has_password;
    }

    /**
     * @brief Returns the password
     * @return
     */
    const std::string& getPassword() const
    {
        return _password;
    }
private:
    bool _has_password;
    std::string _password;
};

enum class ConnectionStatus
{
    Disconnected=0,
    Connecting=1,
    Connected=2,
    Failed=3,
    Blocked=4,
    Disconnecting=5
};

typedef std::vector<WifiLocation> WifiLocationList;
class WifiInfo;

typedef std::shared_ptr<WifiInfo> WifiInfoPtr;


/**
 * @brief Stores the basic Wifi network info
 */
class WifiInfo
{
public:

    /**
     * @brief Creates new wifi info pointer
     * @param ssid human readable name of wifi
     * @param bssid wifi internal identifier (MAC basicly)
     * @return
     */
    static WifiInfoPtr create(const std::string& ssid,
                              const std::string& bssid);

    /**
     * @brief Creates new wifi info pointer
     * @param ssid human readable name of wifi
     * @param bssid wifi internal identifier (MAC basicly)
     * @return
     */
    WifiInfo(const std::string& ssid,
             const std::string& bssid);


    /**
     * @brief The human readbable name of network
     * @return
     */
    const std::string& getSSID() const
    {
        return _ssid;
    }

    /**
     * @brief The device id, basicly the MAC adress of wifi router
     * @return
     */
    const std::string& getBSSID() const
    {
        return _bssid;
    }

    /**
     * @brief Returns true if password is not required
     * @return
     */
    bool isOpened() const
    {
        return _is_opened;
    }

    /**
     * @brief Sets whether the wifi is opened
     * @param value
     */
    void setIsOpened(bool value);

    /**
     * @brief True if the wifi is connected automatically
     * @return
     */
    bool isAutoConnect() const
    {
        return _is_autoconnect;
    }

    /**
     * @brief Sets whether to connect automaticlly
     * @param value
     */
    void setIsAutoConnect(bool value);

    /**
     * @brief True if the password can be shared
     * @return
     */
    bool isShareAllowed() const
    {
        return _is_share_allowed;
    }

    /**
     * @brief Sets whether the password can be shared to the world
     * @param value
     */
    void setIsShareAllowed(bool value);

    /**
     * @brief Returns true if there is password to this netword
     * @return
     */
    bool hasPassword() const;

    /**
     * @brief Returns the current connection status
     * @return
     */
    ConnectionStatus getConnectionStatus() const
    {
        return _connection_status;
    }

    /**
     * @brief Sets new connection status
     */
    void setConnectionStatus(const ConnectionStatus);

    /**
     * @brief Adds location to the history
     * @param location
     */
    void addNewLocation(const WifiLocation& location);

    /**
     * @brief Clears locations history
     */
    void clearLocationsHistory();

    /**
     * @brief Returns the location history
     * @return
     */
    const WifiLocationList& getLocationsHistory() const;

    /**
     * @brief The id of the wifi used to identify it in the system
     * @return
     */
    std::string getID() const
    {
        return _id;
    }

    /**
     * @brief The decode key used for decoding the wifi info in local database
     * @return
     */
    DataPtr getDecodeKey() const
    {
        return _decode_key;
    }

    /**
     * @brief The decode IV used for decoding the wifi info in local database
     * @return
     */
    DataPtr getDecodeIV() const
    {
        return _decode_iv;
    }
private:


    std::string _ssid;
    std::string _bssid;

    bool _is_opened;
    bool _is_autoconnect;
    bool _is_share_allowed;

    WifiPassword _password;
    ConnectionStatus _connection_status;

    WifiLocationList _locations;

    std::string _id;
    DataPtr _decode_key;
    DataPtr _decode_iv;

};

#endif // WIFIINFO_H
