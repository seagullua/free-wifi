LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := FreeWifi
LOCAL_SRC_FILES := FreeWifi.cpp

include $(BUILD_SHARED_LIBRARY)
