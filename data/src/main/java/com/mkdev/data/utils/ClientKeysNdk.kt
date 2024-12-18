package com.mkdev.data.utils

object ClientKeysNdk {
    init {
        System.loadLibrary("clientkeysndk")
    }

    external fun getClientId(): String
    external fun getClientSecret(): String
}