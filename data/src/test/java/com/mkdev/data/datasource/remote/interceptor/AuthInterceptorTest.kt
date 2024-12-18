package com.mkdev.data.datasource.remote.interceptor

import com.mkdev.data.datasource.local.UserLocal
import com.mkdev.data.datasource.local.datastore.UserLocalSource
import com.mkdev.data.datasource.remote.api.AuthApi
import com.mkdev.data.datasource.remote.model.response.base.BaseApiResponse
import com.mkdev.data.datasource.remote.model.response.singIn.SignInAttributesResponse
import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponse
import com.mkdev.data.utils.ApiConfigs
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.http.RealResponseBody
import okio.Buffer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.anyOrNull
import java.net.HttpURLConnection
import javax.inject.Provider
import retrofit2.Response as RetrofitResponse

class AuthInterceptorTest {

    @Mock
    private lateinit var userLocalSource: UserLocalSource

    @Mock
    private lateinit var authApi: AuthApi

    @Mock
    private lateinit var authApiProvider: Provider<AuthApi>

    @Mock
    private lateinit var chain: Interceptor.Chain

    @Mock
    private lateinit var request: Request

    @Mock
    private lateinit var response: Response

    private lateinit var authInterceptor: AuthInterceptor

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        authInterceptor = AuthInterceptor(userLocalSource, authApiProvider)

        `when`(authApiProvider.get()).thenReturn(authApi)
        `when`(chain.request()).thenReturn(request)

        val requestBuilder = Mockito.mock(Request.Builder::class.java)
        `when`(request.newBuilder()).thenReturn(requestBuilder)
        `when`(requestBuilder.removeHeader(anyString())).thenReturn(requestBuilder)
        `when`(
            requestBuilder.header(
                anyString(),
                anyString()
            )
        ).thenReturn(requestBuilder)
        `when`(requestBuilder.build()).thenReturn(request)
    }

    @Test
    fun `intercept should proceed with token when NoAuth header is present`() = runTest {
        // Given
        `when`(request.headers).thenReturn(
            Headers.headersOf(
                ApiConfigs.CUSTOM_HEADER,
                ApiConfigs.NO_AUTH
            )
        )
        `when`(chain.proceed(anyOrNull())).thenReturn(response)

        // When
        val result = authInterceptor.intercept(chain)

        // Then
        verify(chain).proceed(anyOrNull())
        Assert.assertEquals(response, result)
    }

    @Test
    fun `intercept should proceed with token when access token is present`() = runTest {
        // Given
        val accessToken = "accessToken"
        val userLocal = UserLocal.newBuilder().setAccessToken(accessToken).build()
        `when`(request.headers).thenReturn(
            Headers.headersOf(
                "SomeOtherHeader", "SomeValue"
            )
        )
        `when`(userLocalSource.user()).thenReturn(flowOf(userLocal))
        `when`(chain.proceed(anyOrNull())).thenReturn(response)

        // When
        val result = authInterceptor.intercept(chain)

        // Then
        verify(chain).proceed(anyOrNull())
        Assert.assertEquals(response, result)
    }

    @Test
    fun `intercept should refresh token when access token is unauthorized`() = runTest {
        // Given
        val accessToken = "accessToken"
        val newAccessToken = "newAccessToken"
        val refreshToken = "refreshToken"
        val refreshTokenResponse = RetrofitResponse.success(
            BaseApiResponse(
                data = SignInResponse(
                    attributes = SignInAttributesResponse(
                        accessToken = newAccessToken,
                        createdAt = "",
                        expiresIn = "",
                        refreshToken = refreshToken,
                        tokenType = ""
                    ),
                    id = "",
                    type = ""
                ),
                meta = null
            )
        )

        val userLocal = UserLocal.newBuilder()
            .setAccessToken(accessToken)
            .setRefreshToken(refreshToken)
            .build()

        `when`(userLocalSource.user()).thenReturn(flowOf(userLocal))
        `when`(chain.proceed(anyOrNull())).thenReturn(response) // Return 401 for first request
        `when`(response.code).thenReturn(HttpURLConnection.HTTP_UNAUTHORIZED)
        `when`(authApi.refreshToken(anyOrNull())).thenReturn(refreshTokenResponse)
        `when`(request.headers).thenReturn(
            Headers.headersOf(
                "Authorization", "Bearer $newAccessToken",
            )
        )

        // When
        val result = authInterceptor.intercept(chain)

        // Then
        verify(authApi).refreshToken(anyOrNull())
        verify(chain, times(2)).proceed(anyOrNull())
        Assert.assertEquals(response, result)
    }

    @Test
    fun `intercept should return original response when refresh token fails`() = runTest {
        // Given
        val accessToken = "accessToken"
        val refreshToken = "refreshToken"
        val refreshTokenResponse = RetrofitResponse.error<BaseApiResponse<SignInResponse>>(
            HttpURLConnection.HTTP_UNAUTHORIZED,
            RealResponseBody("", 0L, Buffer())
        )

        val userLocal = UserLocal.newBuilder()
            .setAccessToken(accessToken)
            .setRefreshToken(refreshToken)
            .build()

        `when`(userLocalSource.user()).thenReturn(flowOf(userLocal))
        `when`(chain.proceed(anyOrNull())).thenReturn(response)
        `when`(response.code).thenReturn(HttpURLConnection.HTTP_UNAUTHORIZED)
        `when`(authApi.refreshToken(anyOrNull())).thenReturn(
            refreshTokenResponse
        )
        `when`(request.headers).thenReturn(
            Headers.headersOf(
                "Authorization", "Bearer $accessToken",
            )
        )

        // When
        val result = authInterceptor.intercept(chain)

        // Then
        verify(authApi).refreshToken(anyOrNull())
        verify(chain).proceed(anyOrNull())
        Assert.assertEquals(response, result)
    }
}