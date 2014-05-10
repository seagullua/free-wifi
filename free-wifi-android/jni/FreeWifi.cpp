#include <jni.h>

//#undef JNIEXPORT
//#define JNIEXPORT __attribute__ ((visibility("default")))

#include <freewifi/API.h>
#include "platform/PlatformImpl.h"
extern "C"
{
jint JNI_OnLoad(JavaVM *vm, void *reserved)
{
    //JniHelper::setJavaVM(vm);
	API::getInstance()->init(API::PlatformPtr(new PlatformImpl));
    return JNI_VERSION_1_4;
}
}
