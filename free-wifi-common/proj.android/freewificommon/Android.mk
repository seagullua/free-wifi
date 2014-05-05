LOCAL_PATH := $(call my-dir)/../../

include $(CLEAR_VARS)

LOCAL_MODULE := freewificommon_static

LOCAL_MODULE_FILENAME := libfreewificommon

FILE_LIST := $(wildcard $(LOCAL_PATH)/freewifi/*.cpp)
FILE_LIST += $(wildcard $(LOCAL_PATH)/freewifi/platform/*.cpp)
FILE_LIST += $(wildcard $(LOCAL_PATH)/freewifi/crypto/*.cpp)

LOCAL_SRC_FILES := $(FILE_LIST:$(LOCAL_PATH)/%=%)

LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)
LOCAL_CPPFLAGS += -std=c++11

LOCAL_C_INCLUDES := $(LOCAL_PATH)
LOCAL_STATIC_LIBRARIES := jasson_static libtomcrypt_static curl_static zlib_static sqlcipher_static
LOCAL_LDLIBS := -llog
LOCAL_EXPORT_LDLIBS := -llog
include $(BUILD_STATIC_LIBRARY)

$(call import-add-path, $(LOCAL_PATH)/../libraries/jansson/proj.android)
$(call import-add-path, $(LOCAL_PATH)/../libraries/libtomcrypt/proj.android)
$(call import-add-path, $(LOCAL_PATH)/../libraries/curl/proj.android)
$(call import-add-path, $(LOCAL_PATH)/../libraries/zlib/proj.android)
$(call import-add-path, $(LOCAL_PATH)/../libraries/sqlcipher-amalgamation/proj.android)
$(call import-module,jansson)
$(call import-module,libtomcrypt)
$(call import-module,curl)
$(call import-module,zlib)
$(call import-module,sqlcipher)

