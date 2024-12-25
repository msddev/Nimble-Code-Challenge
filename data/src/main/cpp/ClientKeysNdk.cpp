#include <jni.h>
#include "ClientKeysNdk.h"
#include <string>

extern "C" JNIEXPORT jstring JNICALL
// Java_{package_name}_{class_name}_{method_name}
Java_com_mkdev_data_utils_ClientKeysNdk_getClientId(
        JNIEnv *env,
        jobject thiz,
        jstring environment
) {
    const char *env_cstr = env->GetStringUTFChars(environment, 0);
    std::string env_str(env_cstr);
    env->ReleaseStringUTFChars(environment, env_cstr);

    std::string key;
    if (env_str == "production") {
        key = "PRODUCTION_ID";
    } else if (env_str == "staging") {
        key = "STAGING_ID";
    } else { // development
        key = "6GbE8dhoz519l2N_F99StqoOs6Tcmm1rXgda4q__rIw";
    }
    return env->NewStringUTF(key.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_mkdev_data_utils_ClientKeysNdk_getClientSecret(
        JNIEnv *env,
        jobject thiz,
        jstring environment
) {
    const char *env_cstr = env->GetStringUTFChars(environment, 0);
    std::string env_str(env_cstr);
    env->ReleaseStringUTFChars(environment, env_cstr);

    std::string key;
    if (env_str == "production") {
        key = "PRODUCTION_SECRET";
    } else if (env_str == "staging") {
        key = "STAGING_SECRET";
    } else { // development
        key = "_ayfIm7BeUAhx2W1OUqi20fwO3uNxfo1QstyKlFCgHw";
    }
    return env->NewStringUTF(key.c_str());
}

