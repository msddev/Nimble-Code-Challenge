#include <jni.h>
#include "ClientKeysNdk.h"
#include <string>

extern "C" JNIEXPORT jstring JNICALL
// Java_{package_name}_{class_name}_{method_name}
Java_com_mkdev_data_utils_ClientKeysNdk_getClientId(JNIEnv *env, jobject thiz) {
    std::string client_id = "6GbE8dhoz519l2N_F99StqoOs6Tcmm1rXgda4q__rIw";
    return env->NewStringUTF(client_id.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_mkdev_data_utils_ClientKeysNdk_getClientSecret(JNIEnv *env, jobject thiz) {
    std::string client_secret = "_ayfIm7BeUAhx2W1OUqi20fwO3uNxfo1QstyKlFCgHw";
    return env->NewStringUTF(client_secret.c_str());
}