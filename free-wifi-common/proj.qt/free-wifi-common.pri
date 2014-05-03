INCLUDEPATH += $$PWD/../
include($$PWD/../../libraries/jansson/proj.qt/jansson.pri)
include($$PWD/../../libraries/CryptoPP/proj.qt/CryptoPP.pri)

SOURCES += \
    $$PWD/../freewifi/Test.cpp
	
HEADERS += \
    $$PWD/../freewifi/Test.h
	
