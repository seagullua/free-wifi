#include <freewifi/platform/Log.h>
#include "../MainWindow.h"
#include <QDebug>
void log(const char * pszFormat, ...)
{
    static const int MAX_LEN = 2000;
    char szBuf[MAX_LEN];

    va_list ap;
    va_start(ap, pszFormat);
    vsnprintf_s(szBuf, MAX_LEN, MAX_LEN, pszFormat, ap);
    va_end(ap);

    MainWindow* w = MainWindow::getInstance();
    if(w)
    {

        w->writeToLog(szBuf);
    }
    qDebug() << szBuf;
}
