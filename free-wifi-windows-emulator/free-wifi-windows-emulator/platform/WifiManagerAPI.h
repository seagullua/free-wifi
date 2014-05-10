#ifndef WIFIMANAGERAPI_H
#define WIFIMANAGERAPI_H
#include <freewifi/API.h>

class WifiManagerAPI : public API::Platform::WifiManager
{
public:
    WifiManagerAPI();
};

#endif // WIFIMANAGERAPI_H
