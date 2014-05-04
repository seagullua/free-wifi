LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := FreeWifi
LOCAL_SRC_FILES := FreeWifi.cpp

LOCAL_STATIC_LIBRARIES := jasson_static cryptopp_static


include $(BUILD_SHARED_LIBRARY)

$(call import-add-path, $(LOCAL_PATH)/../../libraries/jansson/proj.android)
$(call import-add-path, $(LOCAL_PATH)/../../libraries/CryptoPP/proj.android)
$(call import-module,jansson)
$(call import-module,CryptoPP)