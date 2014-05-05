TEMPLATE = lib
CONFIG += staticlib


CONFIG(debug, debug|release) {
    DESTDIR = $$PWD/bin/debug/
} else {
    DESTDIR = $$PWD/bin/release/
}


DEFINES += _SCL_SECURE_NO_WARNINGS
INCLUDEPATH += ../
#
#include($$PWD/../../libraries/sqlite/proj.qt/sqlite.pri)
include($$PWD/../../libraries/sqlcipher-amalgamation/proj.qt/sqlcipher.pri)
include($$PWD/../../libraries/jansson/proj.qt/jansson.pri)
include($$PWD/../../libraries/libtomcrypt/proj.qt/libtomcrypt.pri)
include($$PWD/../../libraries/curl/proj.qt/curl.pri)
include($$PWD/../../libraries/zlib/proj.qt/zlib.pri)

SOURCES += \
    ../freewifi/Test.cpp \
    ../freewifi/crypto/Base64.cpp \
    ../freewifi/crypto/Data.cpp \
    ../freewifi/crypto/ZLib.cpp

HEADERS += \
    ../freewifi/Test.h \
    ../freewifi/platform/Log.h \
    ../freewifi/crypto/Base64.h \
    ../freewifi/crypto/Data.h \
    ../freewifi/crypto/ZLib.h




