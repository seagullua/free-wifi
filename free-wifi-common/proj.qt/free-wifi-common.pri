INCLUDEPATH += $$PWD/..

CONFIG(debug, debug|release) {
    LIBS += -L$$PWD/bin/debug/ -lfree-wifi-common
    PRE_TARGETDEPS += $$PWD/bin/debug/free-wifi-common.lib
} else {
    LIBS += -L$$PWD/bin/release/ -lfree-wifi-common
    PRE_TARGETDEPS += $$PWD/bin/release/free-wifi-common.lib
}

include($$PWD/../../libraries/libtomcrypt/proj.qt/libtomcrypt.pri)
include($$PWD/../../libraries/jansson/proj.qt/jansson.pri)
include($$PWD/../../libraries/curl/proj.qt/curl.pri)
include($$PWD/../../libraries/zlib/proj.qt/zlib.pri)
#include($$PWD/../../libraries/sqlite/proj.qt/sqlite.pri)
include($$PWD/../../libraries/sqlcipher-amalgamation/proj.qt/sqlcipher.pri)
