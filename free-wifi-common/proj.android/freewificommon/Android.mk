LOCAL_PATH := $(call my-dir)/../../

include $(CLEAR_VARS)

LOCAL_MODULE := freewificommon_static

LOCAL_MODULE_FILENAME := libfreewificommon

FILE_LIST := $(wildcard $(LOCAL_PATH)/freewifi/*.cpp)


LOCAL_SRC_FILES := $(FILE_LIST:$(LOCAL_PATH)/%=%)

LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)

LOCAL_C_INCLUDES := $(LOCAL_PATH)
LOCAL_STATIC_LIBRARIES := jasson_static libtomcrypt_static curl_static

include $(BUILD_STATIC_LIBRARY)

$(call import-add-path, $(LOCAL_PATH)/../libraries/jansson/proj.android)
$(call import-add-path, $(LOCAL_PATH)/../libraries/libtomcrypt/proj.android)
$(call import-add-path, $(LOCAL_PATH)/../libraries/curl/proj.android)
$(call import-module,jansson)
$(call import-module,libtomcrypt)
$(call import-module,curl)
