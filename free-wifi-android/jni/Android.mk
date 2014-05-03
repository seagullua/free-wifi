LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := FreeWifi
LOCAL_SRC_FILES := FreeWifi.cpp

LOCAL_WHOLE_STATIC_LIBRARIES := jasson_static


include $(BUILD_SHARED_LIBRARY)

$(call import-add-path, $(LOCAL_PATH)/../../libraries/jansson/proj.android)
$(call import-module,jansson)
