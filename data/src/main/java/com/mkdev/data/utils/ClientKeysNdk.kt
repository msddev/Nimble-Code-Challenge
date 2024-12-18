package com.mkdev.data.utils

object ClientKeysNdk {
    init {
        System.loadLibrary("ClientKeysNdk")
    }

    external fun getClientId(): String
    external fun getClientSecret(): String
}