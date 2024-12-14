package com.mkdev.data.datasource.local.crypto

interface Crypto {
    fun encrypt(text: ByteArray): ByteArray
    fun decrypt(encryptedData: ByteArray): ByteArray
}