#include "FileSystemAPI.h"
#include "ADJNI.h"

FileSystemAPI::FileSystemAPI()
{

}

std::string FileSystemAPI::getWritablePath()
{
	JNIEnv* env = ADJNI::getEnv();

	jclass cl_App = env->FindClass(cApp);
	jmethodID m_App_getWritablePath = env->GetStaticMethodID(cl_App, "getWritablePath", F(J(cString), None));

	jstring path = (jstring)env->CallStaticObjectMethod(cl_App, m_App_getWritablePath);

	std::string res = ADJNI::toString(env, path);

	env->DeleteLocalRef(path);
	env->DeleteLocalRef(cl_App);

	if(res.size() > 0)
	{
		res += "/";
	}
	return res;
}
