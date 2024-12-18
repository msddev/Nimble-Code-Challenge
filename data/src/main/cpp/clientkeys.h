#ifndef NIMBLE_CODE_CHALLENGE_CLIENTKEYS_H
#define NIMBLE_CODE_CHALLENGE_CLIENTKEYS_H

#include <jni.h>

extern "C" {
JNIEXPORT jstring JNICALL
Java_com_mkdev_data_utils_ClientKeysNdk_getClientId(JNIEnv *env, jobject thiz);

JNIEXPORT jstring JNICALL
Java_com_mkdev_data_utils_ClientKeysNdk_getClientSecret(JNIEnv *env, jobject thiz);
}

#endif //NIMBLE_CODE_CHALLENGE_CLIENTKEYS_H