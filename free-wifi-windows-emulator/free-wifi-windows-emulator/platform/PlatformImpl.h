#ifndef PLATFORMIMPL_H
#define PLATFORMIMPL_H
#include <freewifi/API.h>

class PlatformImpl : public API::Platform
{
public:
    PlatformImpl();

    FileSystemAPI getFileSystemAPI();
    WifiManagerAPI getWifiManagerAPI();
    FreeWifiAPI getFreeWifiAPI();
};

#endif // PLATFORMIMPL_H
