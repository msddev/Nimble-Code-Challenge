package com.mkdev.data.utils

import com.mkdev.data.BuildConfig
import javax.inject.Inject

class ClientKeysNdkWrapper @Inject constructor() {
    fun getClientId(): String {
        return ClientKeysNdk.getClientId(BuildConfig.FLAVOR)
    }

    fun getClientSecret(): String {
        return ClientKeysNdk.getClientSecret(BuildConfig.FLAVOR)
    }
}

private object ClientKeysNdk {
    init {
        System.loadLibrary("ClientKeysNdk")
    }

    external fun getClientId(flavor: String): String
    external fun getClientSecret(flavor: String): String
}
