package com.mkdev.data.datasource.local.crypto

import com.google.crypto.tink.Aead
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CryptoImplTest {

    @Mock
    private lateinit var aead: Aead

    private lateinit var cryptoImpl: CryptoImpl

    @Before
    fun setUp() {
        cryptoImpl = CryptoImpl(aead)
    }

    @Test
    fun `should return encrypted data when encrypt is called`() {
        // Given
        val text = "Hello, world!".toByteArray()
        val encryptedData = "Encrypted data".toByteArray()
        `when`(aead.encrypt(text, null)).thenReturn(encryptedData)

        // When
        val result = cryptoImpl.encrypt(text)

        // Then
        assertArrayEquals(encryptedData, result)
    }

    @Test
    fun `should return decrypted data when decrypt is called with non-empty data`() {
        // Given
        val encryptedData = "Encrypted data".toByteArray()
        val decryptedData = "Hello, world!".toByteArray()
        `when`(aead.decrypt(encryptedData, null)).thenReturn(decryptedData)

        // When
        val result = cryptoImpl.decrypt(encryptedData)

        // Then
        assertArrayEquals(decryptedData, result)
    }

    @Test
    fun `should return empty data when decrypt is called with empty data`() {
        // Given
        val encryptedData = ByteArray(0)

        // When
        val result = cryptoImpl.decrypt(encryptedData)

        // Then
        assertArrayEquals(encryptedData, result)
    }
}