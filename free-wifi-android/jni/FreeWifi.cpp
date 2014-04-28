#include <jni.h>

extern "C"
{
jint Java_app_freewifi_MainActivity_addNumbers(JNIEnv*  env, jclass, jint a, jint b)
{
    return a+b;
}
}
