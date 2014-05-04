APP_STL := gnustl_static
APP_OPTIM := release
APP_CPPFLAGS := -frtti -fexceptions 
APP_CFLAGS += -Wno-error=format-security

NDK_TOOLCHAIN_VERSION := 4.8