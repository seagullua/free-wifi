#include <jni.h>

#undef JNIEXPORT
#define JNIEXPORT __attribute__ ((visibility("default")))

#include <freewifi/Test.h>
extern "C"
{
JNIEXPORT jint Java_app_freewifi_MainActivity_addNumbers(JNIEnv*  env, jclass, jint a, jint b)
{
	runTests();
	return 3;
}
}
