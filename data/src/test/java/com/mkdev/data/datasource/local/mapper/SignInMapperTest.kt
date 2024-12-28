package com.mkdev.data.datasource.local.mapper

import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponse
import com.mkdev.data.factory.SignInResponseFactory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SignInMapperTest {

    private val signInMapper = SignInMapper()

    @Test
    fun `mapToUserLocal should return null when signInResponse is null`() {
        // Given
        val signInResponse: SignInResponse? = null

        // When
        val result = signInMapper.mapToUserLocal(signInResponse)

        // Then
        assertNull(result)
    }

    @Test
    fun `mapToUserLocal should return UserLocal when signInResponse is not null`() {
        // Given
        val signInResponse = SignInResponseFactory.createSignInResponse().data

        // When
        val result = signInMapper.mapToUserLocal(signInResponse)

        // Then
        // Assert that the result is not null and then access its properties
        val userLocal = requireNotNull(result)
        assertEquals(signInResponse.accessToken, userLocal.accessToken)
        assertEquals(signInResponse.refreshToken, userLocal.refreshToken)
        assertEquals(signInResponse.createdAt.toString(), userLocal.createdAt)
        assertEquals(signInResponse.expiresIn.toString(), userLocal.expiresIn)
        assertEquals(signInResponse.tokenType, userLocal.tokenType)
    }
}