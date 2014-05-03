TEMPLATE = subdirs
CONFIG += ordered

SUBDIRS += cryptoPP
cryptoPP.file = $$PWD/../libraries/cryptoPP/proj.qt/cryptoPP.pro

SUBDIRS += jansson
jansson.file = $$PWD/../libraries/jansson/proj.qt/jansson.pro

SUBDIRS += curl
curl.file = $$PWD/../libraries/curl/proj.qt/curl.pro

SUBDIRS += free-wifi-common
free-wifi-common.file = $$PWD/../free-wifi-common/proj.qt/free-wifi-common.pro
free-wifi-common.depends = cryptoPP jansson curl

SUBDIRS += free-wifi-windows-emulator
free-wifi-windows-emulator.depends = free-wifi-common
