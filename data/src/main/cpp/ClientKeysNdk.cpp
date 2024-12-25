#include <jni.h>
#include "ClientKeysNdk.h"
#include <string>

extern "C" JNIEXPORT jstring JNICALL
// Java_{package_name}_{class_name}_{method_name}
Java_com_mkdev_data_utils_ClientKeysNdk_getClientId(
        JNIEnv *env,
        jobject thiz,
        jstring environment,
        jstring buildType
) {
    const char *env_cstr = env->GetStringUTFChars(environment, 0);
    std::string env_str(env_cstr);
    env->ReleaseStringUTFChars(environment, env_cstr);

    const char *build_type_cstr = env->GetStringUTFChars(buildType, 0);
    std::string build_type_str(build_type_cstr);
    env->ReleaseStringUTFChars(buildType, build_type_cstr);

    std::string key;

    if (env_str == "production") {
        if (build_type_str == "debug") {
            key = "PRODUCTION_DEBUG_ID";
        } else { // release
            key = "PRODUCTION_RELEASE_ID";
        }
    } else if (env_str == "staging") {
        if (build_type_str == "debug") {
            key = "STAGING_DEBUG_ID";
        } else { // release
            key = "STAGING_RELEASE_ID";
        }
    } else { // development
        if (build_type_str == "debug") {
            key = "6GbE8dhoz519l2N_F99StqoOs6Tcmm1rXgda4q__rIw";
        } else { // release
            key = "DEVELOPMENT_RELEASE_ID";
        }
    }

    return env->NewStringUTF(key.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_mkdev_data_utils_ClientKeysNdk_getClientSecret(
        JNIEnv *env,
        jobject thiz,
        jstring environment,
        jstring buildType
) {
    const char *env_cstr = env->GetStringUTFChars(environment, 0);
    std::string env_str(env_cstr);
    env->ReleaseStringUTFChars(environment, env_cstr);

    const char *build_type_cstr = env->GetStringUTFChars(buildType, 0);
    std::string build_type_str(build_type_cstr);
    env->ReleaseStringUTFChars(buildType, build_type_cstr);

    std::string key;

    if (env_str == "production") {
        if (build_type_str == "debug") {
            key = "PRODUCTION_DEBUG_SECRET_KEY";
        } else { // release
            key = "PRODUCTION_RELEASE_SECRET_KEY";
        }
    } else if (env_str == "staging") {
        if (build_type_str == "debug") {
            key = "STAGING_DEBUG_SECRET_KEY";
        } else { // release
            key = "STAGING_RELEASE_SECRET_KEY";
        }
    } else { // development
        if (build_type_str == "debug") {
            key = "_ayfIm7BeUAhx2W1OUqi20fwO3uNxfo1QstyKlFCgHw";
        } else { // release
            key = "DEVELOPMENT_RELEASE_SECRET_KEY";
        }
    }

    return env->NewStringUTF(key.c_str());
}

