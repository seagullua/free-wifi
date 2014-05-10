#include "ADJNI.h"
#include <freewifi/platform/Log.h>


JavaVM* ADJNI::_java_vm = nullptr;

void ADJNI::setJavaVM(JavaVM* vm)
{
	_java_vm = vm;
}

JNIEnv* ADJNI::getEnv()
{
    JNIEnv* env = nullptr;

    if(_java_vm)
    {
    	jint ok = _java_vm->GetEnv((void**)&env, JNI_VERSION_1_4);
    	if(ok == JNI_OK)
    	{
    		ok = _java_vm->AttachCurrentThread(&env, 0);
    		if(ok < 0)
    		{
    			log("ERROR: JAVA AttachCurrentThread failed");
    			env = nullptr;
    		}
    	}
    	else
    	{
    		log("ERROR: JAVA GetEnv failed");
    		env = nullptr;
    	}
    }
    else
    {
    	log("ERROR: JAVA VM not defined");
    }

    return env;
}



//#define CocosActivity "org/cocos2dx/lib/Cocos2dxActivity"

//jobject ADJNI::getActivity(JNIEnv* env)
//{
//    jclass cocos_activity = env->FindClass(CocosActivity);
//    jmethodID get_activity = env->GetStaticMethodID(
//                cocos_activity, "getContext", F(J(cContext), None));
//    jobject activity = env->CallStaticObjectMethod(cocos_activity, get_activity);
//    return activity;
//}
