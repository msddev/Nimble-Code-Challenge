package com.mkdev.data.utils

import javax.inject.Inject

class ClientKeysNdkWrapper @Inject constructor() {
    fun getClientId(): String {
        return ClientKeysNdk.getClientId()
    }

    fun getClientSecret(): String {
        return ClientKeysNdk.getClientSecret()
    }
}

private object ClientKeysNdk {
    init {
        System.loadLibrary("ClientKeysNdk")
    }

    external fun getClientId(): String
    external fun getClientSecret(): String
}
