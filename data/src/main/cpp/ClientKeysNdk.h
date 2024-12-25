#ifndef NIMBLE_CODE_CHALLENGE_CLIENTKEYSNDK_H
#define NIMBLE_CODE_CHALLENGE_CLIENTKEYSNDK_H

#include <jni.h>

extern "C" {
JNIEXPORT jstring JNICALL
Java_com_mkdev_data_utils_ClientKeysNdk_getClientId(
        JNIEnv *env,
        jobject thiz,
        jstring environment,
        jstring buildType
);

JNIEXPORT jstring JNICALL
Java_com_mkdev_data_utils_ClientKeysNdk_getClientSecret(
        JNIEnv *env,
        jobject thiz,
        jstring environment,
        jstring buildType
);
}

#endif //NIMBLE_CODE_CHALLENGE_CLIENT_KEYS_NDK_H