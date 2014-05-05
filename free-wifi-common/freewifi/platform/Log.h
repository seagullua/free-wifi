#ifndef LOG_H
#define LOG_H
#include <string>
void log(const char * pszFormat, ...);
inline void log(const std::string& s)
{
    log("%s", s.c_str());
}

#endif // LOG_H
