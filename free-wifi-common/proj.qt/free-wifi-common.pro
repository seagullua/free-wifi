TEMPLATE = lib
CONFIG += staticlib


CONFIG(debug, debug|release) {
    DESTDIR = $$PWD/bin/debug/
} else {
    DESTDIR = $$PWD/bin/release/
}

INCLUDEPATH += ../
#
include($$PWD/../../libraries/jansson/proj.qt/jansson.pri)
include($$PWD/../../libraries/CryptoPP/proj.qt/cryptoPP.pri)
include($$PWD/../../libraries/curl/proj.qt/curl.pri)

SOURCES += \
    ../freewifi/Test.cpp

HEADERS += \
    ../freewifi/Test.h




