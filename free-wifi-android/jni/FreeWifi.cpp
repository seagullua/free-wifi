#include <jni.h>

//#undef JNIEXPORT
//#define JNIEXPORT __attribute__ ((visibility("default")))

#include <freewifi/API.h>
#include "ADJNI.h"
#include "platform/PlatformImpl.h"
extern "C"
{
jint JNI_OnLoad(JavaVM *vm, void *reserved)
{
	ADJNI::setJavaVM(vm);
    return JNI_VERSION_1_4;
}
void Java_app_freewifi_App_initLibrary(JNIEnv* env, jclass cl)
{
	API::getInstance()->init(API::PlatformPtr(new PlatformImpl));
}
}
