package com.mkdev.data.utils

import com.mkdev.data.BuildConfig
import javax.inject.Inject

class ClientKeysNdkWrapper @Inject constructor() {
    fun getClientId(): String {
        return ClientKeysNdk.getClientId(BuildConfig.FLAVOR, BuildConfig.BUILD_TYPE)
    }

    fun getClientSecret(): String {
        return ClientKeysNdk.getClientSecret(BuildConfig.FLAVOR, BuildConfig.BUILD_TYPE)
    }
}

private object ClientKeysNdk {
    init {
        System.loadLibrary("ClientKeysNdk")
    }

    external fun getClientId(flavor: String, buildType: String): String
    external fun getClientSecret(flavor: String, buildType: String): String
}
