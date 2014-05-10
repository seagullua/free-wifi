#include "WifiInfo.h"
#include "KeyStorage.h"
WifiLocation::WifiLocation()
    : _is_defined(false),
      _lat(0),
      _lon(0),
      _level(0)
{

}

WifiLocation::WifiLocation(double lat, double lon, const WifiLevel level)
    : _is_defined(true),
      _lat(lat),
      _lon(lon),
      _level(level)
{

}

WifiPassword::WifiPassword()
    : _has_password(false),
      _password()
{

}

WifiPassword::WifiPassword(std::string password)
    : _has_password(true),
      _password(password)
{
}


WifiInfo::WifiInfo(const std::string &ssid, const std::string &bssid)
    :
      _ssid(ssid),
      _bssid(bssid),

      _is_opened(false),
      _is_autoconnect(false),
      _is_share_allowed(false),

      _password(),
      _connection_status(ConnectionStatus::Disconnected),

      _locations(),

      _id(),
      _decode_key(nullptr),
      _decode_iv(nullptr)
{
    _id = KeyStorage::getWifiID(_ssid, _bssid);
    KeyStorage::DecodeKey k = KeyStorage::getWifiDecodeKey(_ssid, _bssid);

    _decode_key = k.key;
    _decode_iv = k.iv;
}

WifiInfoPtr WifiInfo::create(
        const std::string& ssid,
        const std::string& bssid)
{
    return std::make_shared<WifiInfo>(ssid, bssid);
}

void WifiInfo::setIsOpened(bool value)
{
    _is_opened = value;
}

void WifiInfo::setIsAutoConnect(bool value)
{
    _is_autoconnect = value;
}

void WifiInfo::setIsShareAllowed(bool value)
{
    _is_share_allowed = value;
}

bool WifiInfo::hasPassword() const
{
    return _password.hasPassword();
}
void WifiInfo::setConnectionStatus(const ConnectionStatus s)
{
    _connection_status = s;
}

void WifiInfo::addNewLocation(const WifiLocation& location)
{
    _locations.push_back(location);
}

void WifiInfo::clearLocationsHistory()
{
    _locations.clear();
}

const WifiLocationList& WifiInfo::getLocationsHistory() const
{
    return _locations;
}
