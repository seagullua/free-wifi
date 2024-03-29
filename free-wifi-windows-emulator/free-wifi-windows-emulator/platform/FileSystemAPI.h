#ifndef FILESYSTEMAPI_H
#define FILESYSTEMAPI_H
#include <freewifi/API.h>

class FileSystemAPI : public API::Platform::FileSystem
{
public:
    FileSystemAPI();
    std::string getWritablePath();
};

#endif // FILESYSTEMAPI_H
