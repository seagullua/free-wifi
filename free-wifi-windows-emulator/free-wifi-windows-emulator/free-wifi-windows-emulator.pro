#-------------------------------------------------
#
# Project created by QtCreator 2014-05-03T12:35:46
#
#-------------------------------------------------
//INCLUDEPATH += aaa
QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = free-wifi-windows-emulator
TEMPLATE = app

CONFIG(debug, debug|release) {
    DESTDIR = $$PWD/../bin/debug/
} else {
    DESTDIR = $$PWD/../bin/release/
}

include($$PWD/../../free-wifi-common/proj.qt/free-wifi-common.pri)

SOURCES += main.cpp\
        MainWindow.cpp

HEADERS  += MainWindow.h

FORMS    += MainWindow.ui
