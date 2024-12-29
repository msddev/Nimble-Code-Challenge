package com.mkdev.data.datasource.remote.api

import com.mkdev.data.datasource.remote.model.response.resetPassword.ResetPasswordMetaResponse
import com.mkdev.data.datasource.remote.model.response.resetPassword.ResetPasswordResponse
import com.mkdev.data.datasource.remote.model.response.resetPassword.ResetPasswordResponseModel
import com.mkdev.data.factory.RefreshTokenRequestFactory
import com.mkdev.data.factory.ResetPasswordRequestFactory
import com.mkdev.data.factory.SignInRequestFactory
import com.mkdev.data.factory.SignInResponseFactory
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AuthApiTest {

    @MockK
    private lateinit var authApi: AuthApi

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `signIn should call correct endpoint with correct request body and headers`() =
        runBlocking {
            // Given
            val signInRequest = SignInRequestFactory.createSignInRequest()
            val signInResponse = SignInResponseFactory.createSignInResponse()
            coEvery { authApi.signIn(signInRequest) } returns signInResponse

            // When
            val result = authApi.signIn(signInRequest)

            // Then
            coVerify(exactly = 1) {
                authApi.signIn(
                    withArg {
                        Assert.assertEquals(signInRequest.grantType, it.grantType)
                        Assert.assertEquals(signInRequest.email, it.email)
                        Assert.assertEquals(signInRequest.password, it.password)
                        Assert.assertEquals(signInRequest.clientId, it.clientId)
                        Assert.assertEquals(signInRequest.clientSecret, it.clientSecret)
                    }
                )
            }
            Assert.assertEquals(signInResponse, result)
        }

    @Test
    fun `refreshToken should call correct endpoint with correct request body and headers`() =
        runBlocking {
            // Given
            val refreshTokenRequest = RefreshTokenRequestFactory.createRefreshTokenRequest()
            val signInResponse = SignInResponseFactory.createSignInResponse()
            val response = Response.success(signInResponse)
            coEvery { authApi.refreshToken(refreshTokenRequest) } returns response

            // When
            val result = authApi.refreshToken(refreshTokenRequest)

            // Then
            coVerify(exactly = 1) {
                authApi.refreshToken(
                    withArg {
                        Assert.assertEquals(refreshTokenRequest.grantType, it.grantType)
                        Assert.assertEquals(refreshTokenRequest.refreshToken, it.refreshToken)
                        Assert.assertEquals(refreshTokenRequest.clientId, it.clientId)
                        Assert.assertEquals(refreshTokenRequest.clientSecret, it.clientSecret)
                    }
                )
            }
            Assert.assertEquals(response, result)
        }

    @Test
    fun `resetPassword should call correct endpoint with correct request body and headers`() =
        runBlocking {
            // Given
            val resetPasswordRequest = ResetPasswordRequestFactory.createResetPasswordRequest()
            val response = ResetPasswordResponseModel(
                data = ResetPasswordResponse(),
                rootMeta = ResetPasswordMetaResponse(message = "")
            )
            coEvery { authApi.resetPassword(resetPasswordRequest) } returns response

            // When
            val result = authApi.resetPassword(resetPasswordRequest)

            // Then
            coVerify(exactly = 1) {
                authApi.resetPassword(
                    withArg {
                        Assert.assertEquals(resetPasswordRequest.user.email, it.user.email)
                        Assert.assertEquals(resetPasswordRequest.clientId, it.clientId)
                        Assert.assertEquals(resetPasswordRequest.clientSecret, it.clientSecret)
                    }
                )
            }
            Assert.assertEquals(response, result)
        }
}