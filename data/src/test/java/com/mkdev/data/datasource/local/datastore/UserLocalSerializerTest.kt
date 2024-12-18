package com.mkdev.data.datasource.local.datastore

import androidx.datastore.core.CorruptionException
import com.ibm.icu.impl.Assert.fail
import com.mkdev.data.datasource.local.UserLocal
import com.mkdev.data.datasource.local.crypto.Crypto
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

@RunWith(MockitoJUnitRunner::class)
class UserLocalSerializerTest {

    @Mock
    private lateinit var crypto: Crypto

    private lateinit var userLocalSerializer: UserLocalSerializer

    @Before
    fun setUp() {
        userLocalSerializer = UserLocalSerializer(crypto)
    }

    @Test
    fun `defaultValue should return USER_LOCAL_NULL`() {
        Assert.assertEquals(USER_LOCAL_NULL, userLocalSerializer.defaultValue)
    }

    @Test
    fun `readFrom should return UserLocal object when input is valid`() = runTest {
        // Given
        val userLocal = UserLocal.getDefaultInstance()
        val encryptedBytes = userLocal.toByteArray()
        val inputStream = ByteArrayInputStream(encryptedBytes)
        `when`(crypto.decrypt(encryptedBytes)).thenReturn(userLocal.toByteArray())

        // When
        val result = userLocalSerializer.readFrom(inputStream)

        // Then
        Assert.assertEquals(userLocal, result)
    }

    @Test
    fun `readFrom should throw CorruptionException when input is invalid`() = runTest {
        // Given
        val invalidBytes = byteArrayOf(1, 2, 3)
        val inputStream = ByteArrayInputStream(invalidBytes)
        `when`(crypto.decrypt(invalidBytes)).thenReturn(invalidBytes)

        // When & Then
        try {
            userLocalSerializer.readFrom(inputStream)
            fail("Expected CorruptionException to be thrown") // Fail if exception is not thrown
        } catch (e: CorruptionException) {
            Assert.assertEquals("Cannot read proto.", e.message) // Assert on exception message
        }
    }

    @Test
    fun `writeTo should write UserLocal object to output stream`() = runTest {
        // Given
        val userLocal = UserLocal.getDefaultInstance()
        val encryptedBytes = userLocal.toByteArray()
        val outputStream = ByteArrayOutputStream()
        `when`(crypto.encrypt(userLocal.toByteArray())).thenReturn(encryptedBytes)

        // When
        userLocalSerializer.writeTo(userLocal, outputStream)

        // Then
        Assert.assertArrayEquals(encryptedBytes, outputStream.toByteArray())
    }
}