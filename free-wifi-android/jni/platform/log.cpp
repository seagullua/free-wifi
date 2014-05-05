#include <freewifi/platform/Log.h>
#include <android/log.h>
#include <stdio.h>

void log(const char * pszFormat, ...)
{
    static const int MAX_LEN = 2000;
    char buf[MAX_LEN];

    va_list args;
    va_start(args, pszFormat);
    vsnprintf(buf, MAX_LEN, pszFormat, args);
    va_end(args);

    __android_log_print(ANDROID_LOG_DEBUG, "free-wifi-common", "%s", buf);
}