LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := FreeWifi
LOCAL_SRC_FILES := FreeWifi.cpp

LOCAL_STATIC_LIBRARIES := freewificommon_static


include $(BUILD_SHARED_LIBRARY)

$(call import-add-path, $(LOCAL_PATH)/../../free-wifi-common/proj.android)
$(call import-module,freewificommon)
