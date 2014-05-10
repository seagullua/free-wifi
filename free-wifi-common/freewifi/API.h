#ifndef API_H
#define API_H
#include <memory>
#include <string>

class API
{
public:
    /**
     * @brief Class which holds all virtual methods which should
     * be implemented by the platform
     */
    class Platform
    {
    public:
        /**
         * @brief Class holds all virtual methods which should be implemented
         * for filesystem manipulation
         */
        class FileSystem
        {
        public:
            virtual ~FileSystem()
            {}
            /**
             * @brief Returns writable path with slash at the end
             * @return
             */
            virtual std::string getWritablePath()=0;
        };
        typedef std::shared_ptr<FileSystem> FileSystemAPI;

        /**
         * @brief Class which holds interface for working with wifi
         */
        class WifiManager
        {
        public:
            virtual ~WifiManager()
            {}
        };
        typedef std::shared_ptr<WifiManager> WifiManagerAPI;

        /**
         * @brief Class which holds interface for working with FreeWifi library
         */
        class FreeWifi
        {
        public:
            virtual ~FreeWifi()
            {}
        };
        typedef std::shared_ptr<FreeWifi> FreeWifiAPI;

        /**
         * @brief Method which will be called once to recieve file api
         * @return
         */
        virtual FileSystemAPI getFileSystemAPI() = 0;

        /**
         * @brief Method which will be called once to recieve wifi api
         * @return
         */
        virtual WifiManagerAPI getWifiManagerAPI() = 0;

        /**
         * @brief Method which will be called once to recieve free wifi api
         * @return
         */
        virtual FreeWifiAPI getFreeWifiAPI() = 0;

        virtual ~Platform()
        {}
    };
    typedef std::shared_ptr<Platform> PlatformPtr;

    /**
     * @brief Must be called before any other method
     * @param platform_implementation class which provides all platform specific implementations
     */
    void init(PlatformPtr platform_implementation);

    static API* getInstance();

private:
    inline bool isInitPerformed()
    {
        return _init_performed && _api_filesystem && _api_wifimanager && _api_freewifi;
    }
    bool initDatabase();

    API();

    Platform::FileSystemAPI _api_filesystem;
    Platform::WifiManagerAPI _api_wifimanager;
    Platform::FreeWifiAPI _api_freewifi;
    bool _init_performed;


};

#endif // API_H
