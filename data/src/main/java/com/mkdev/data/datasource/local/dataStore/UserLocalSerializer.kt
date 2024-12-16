package com.mkdev.data.datasource.local.dataStore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.mkdev.data.datasource.local.UserLocal
import com.mkdev.data.datasource.local.crypto.Crypto
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserLocalSerializer @Inject constructor(
    private val crypto: Crypto
) : Serializer<UserLocal> {
    override val defaultValue: UserLocal get() = USER_LOCAL_NULL

    override suspend fun readFrom(input: InputStream): UserLocal {
        return try {
            UserLocal.parseFrom(
                input
                    .readBytes()
                    .let(crypto::decrypt)
            )
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: UserLocal, output: OutputStream) {
        output.write(crypto.encrypt(t.toByteArray()))
        output.flush()
    }
}
