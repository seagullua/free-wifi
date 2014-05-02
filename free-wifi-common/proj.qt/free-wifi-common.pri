INCLUDEPATH += $$PWD/../Classes
include($$PWD/../../libraries/jansson/proj.qt/jansson.pri)
include($$PWD/../../libraries/CryptoPP/proj.qt/CryptoPP.pri)

SOURCES += \
    $$PWD/../Classes/Test.cpp
	
HEADERS += \
    $$PWD/../Classes/Test.h
	
