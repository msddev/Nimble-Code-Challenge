package com.mkdev.data.datasource.local.crypto

import com.google.crypto.tink.Aead
import javax.inject.Inject
import kotlin.collections.isNotEmpty

class CryptoImpl @Inject constructor(
    private val aead: Aead
) : Crypto {
    override fun encrypt(text: ByteArray): ByteArray {
        return aead.encrypt(text, null)
    }

    override fun decrypt(encryptedData: ByteArray): ByteArray {
        return if (encryptedData.isNotEmpty()) {
            aead.decrypt(encryptedData, null)
        } else {
            encryptedData
        }
    }
}
