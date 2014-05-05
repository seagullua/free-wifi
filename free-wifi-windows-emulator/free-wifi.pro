TEMPLATE = subdirs
CONFIG += ordered

SUBDIRS += libtomcrypt
libtomcrypt.file = $$PWD/../libraries/libtomcrypt/proj.qt/libtomcrypt.pro

SUBDIRS += jansson
jansson.file = $$PWD/../libraries/jansson/proj.qt/jansson.pro

SUBDIRS += curl
curl.file = $$PWD/../libraries/curl/proj.qt/curl.pro

SUBDIRS += zlib
zlib.file = $$PWD/../libraries/zlib/proj.qt/zlib.pro

SUBDIRS += sqlcipher
sqlcipher.file = $$PWD/../libraries/sqlcipher-amalgamation/proj.qt/sqlcipher.pro
sqlcipher.depends = libtomcrypt

#SUBDIRS += sqlite
#sqlite.file = $$PWD/../libraries/sqlite/proj.qt/sqlite.pro

SUBDIRS += free-wifi-common
free-wifi-common.file = $$PWD/../free-wifi-common/proj.qt/free-wifi-common.pro
free-wifi-common.depends = libtomcrypt jansson curl zlib sqlite

SUBDIRS += free-wifi-windows-emulator
free-wifi-windows-emulator.depends = free-wifi-common
