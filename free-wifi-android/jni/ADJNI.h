#ifndef ADJNI_Library_H
#define ADJNI_Library_H

#ifdef _WIN32
#include "ADLib/../../jni.h"
#endif
#include <jni.h>

#define F(Res, Params) "(" Params ")" Res
#define Arr(Code) "[" Code

#define J(Class) "L" Class ";"

#define Void "V"
#define None ""
#define Bool "Z"
#define Float "F"
#define Int "I"
#define Long "J"

#define cActivity "android/app/Activity"
#define cContext "android/content/Context"
#define cString "java/lang/String"
#define cIntent "android/content/Intent"
#define cApp "app/freewifi/App"
#include <string>
#include <freewifi/platform/Log.h>
class ADJNI
{
public:

	static void setJavaVM(JavaVM*);
    static JNIEnv* getEnv();

    template<class T>
    static T GRef(JNIEnv* env, T ref)
    {
        T g_ref = (T)env->NewGlobalRef(ref);
        env->DeleteLocalRef(ref);
        return g_ref;
    }

    static void clearExceptions(JNIEnv* env)
    {
        if(env->ExceptionOccurred())
            env->ExceptionClear();
    }

    //static jobject getActivity(JNIEnv* env);
    /*static jobject getContext(JNIEnv* env)
    {
        return getActivity(env);
    }*/

    static std::string toString(JNIEnv* env, jstring str)
    {
        const char *s = env->GetStringUTFChars(str,NULL);
        std::string res(s);
        env->ReleaseStringUTFChars(str,s);
        return res;
    }
private:
    static JavaVM *_java_vm;
};


#endif
