INCLUDEPATH += $$PWD/..

CONFIG(debug, debug|release) {
    LIBS += -L$$PWD/bin/debug/ -lfree-wifi-common
} else {
    LIBS += -L$$PWD/bin/release/ -lfree-wifi-common
}

include($$PWD/../../libraries/libtomcrypt/proj.qt/libtomcrypt.pri)
include($$PWD/../../libraries/jansson/proj.qt/jansson.pri)
include($$PWD/../../libraries/curl/proj.qt/curl.pri)
