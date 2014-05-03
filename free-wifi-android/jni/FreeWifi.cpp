#include <jni.h>
#include <jansson.h>
extern "C"
{
jint Java_app_freewifi_MainActivity_addNumbers(JNIEnv*  env, jclass, jint a, jint b)
{
	json_error_t error;
	json_t *root = json_loads("aaa", 0, &error);
    return a+b;
}
}
