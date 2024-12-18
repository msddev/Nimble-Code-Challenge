package com.mkdev.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mkdev.data.datasource.local.datastore.UserLocalSource
import com.mkdev.data.datasource.local.mapper.SignInMapper
import com.mkdev.data.datasource.remote.api.AuthApi
import com.mkdev.data.datasource.remote.model.response.base.BaseApiResponse
import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponse
import com.mkdev.data.factory.MetaResponseFactory
import com.mkdev.data.factory.SignInRequestFactory
import com.mkdev.data.factory.SignInResponseFactory
import com.mkdev.data.factory.UserLocalFactory
import com.mkdev.data.utils.ApiErrorHandler
import com.mkdev.data.utils.ApiException.HttpError
import com.mkdev.data.utils.ClientKeysNdkWrapper
import com.mkdev.domain.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.anyOrNull
import retrofit2.Response
import retrofit2.Response as RetrofitResponse

@ExperimentalCoroutinesApi
class AuthRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var testDispatcher: TestDispatcher

    @Mock
    private lateinit var userLocalSource: UserLocalSource

    @Mock
    private lateinit var authApi: AuthApi

    @Mock
    private lateinit var apiErrorHandler: ApiErrorHandler

    @Mock
    private lateinit var signInMapper: SignInMapper

    private lateinit var authRepository: AuthRepositoryImpl

    @Mock
    lateinit var clientKeysNdkWrapperMock: ClientKeysNdkWrapper

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        authRepository = AuthRepositoryImpl(
            userLocalSource,
            authApi,
            apiErrorHandler,
            signInMapper,
            clientKeysNdkWrapperMock
        )

        testDispatcher = StandardTestDispatcher()
    }

    @Test
    fun `signIn should emit loading and then success on success`() = runTest {
        // Given
        val signInRequest = SignInRequestFactory.createSignInRequest()
        val signInResponse = SignInResponseFactory.createSignInResponse()
        val metaResponse = MetaResponseFactory.createMetaResponse()
        val userLocal = UserLocalFactory.createUserLocal()
        val apiResponse = RetrofitResponse.success(
            BaseApiResponse(
                signInResponse,
                metaResponse
            )
        )
        `when`(clientKeysNdkWrapperMock.getClientId()).thenReturn("mocked_client_id")
        `when`(clientKeysNdkWrapperMock.getClientSecret()).thenReturn("mocked_client_secret")
        `when`(authApi.signIn(anyOrNull())).thenReturn(apiResponse)
        `when`(signInMapper.mapToUserLocal(signInResponse)).thenReturn(userLocal)

        // When-Then
        authRepository.signIn(
            signInRequest.grantType,
            signInRequest.email,
            signInRequest.password
        ).test {
            assertThat(awaitItem()).isInstanceOf(Resource.Loading::class.java)
            assertThat(awaitItem()).isInstanceOf(Resource.Success::class.java)
            awaitComplete()
        }
    }

    @Test
    fun `signIn should emit loading and then error on failure`() = runTest {
        // Given
        val signInRequest = SignInRequestFactory.createSignInRequest()
        val errorResponse =
            Response.error<BaseApiResponse<SignInResponse>>(400, "client error".toResponseBody())
        val expectedApiException = HttpError(400, "Client error message")

        `when`(authApi.signIn(anyOrNull())).thenReturn(errorResponse)
        `when`(apiErrorHandler.handleError(anyOrNull())).thenReturn(expectedApiException)

        // When-Then
        authRepository.signIn(
            signInRequest.grantType,
            signInRequest.email,
            signInRequest.password
        ).test {
            assertThat(awaitItem()).isInstanceOf(Resource.Loading::class.java)
            assertThat(awaitItem()).isInstanceOf(Resource.Error::class.java)
            awaitComplete()
        }
    }

    @Test
    fun `signOut should update userLocalSource and emit success`() = runTest {
        // Given-When-Then
        authRepository.signOut().test {
            assertThat(awaitItem()).isInstanceOf(Resource.Success::class.java)
            awaitComplete()
        }

        verify(userLocalSource).update(anyOrNull())
    }

    @Test
    fun `isUserSignedIn should return false when userLocal is null`() = runTest {
        // Given
        `when`(userLocalSource.user()).thenReturn(flowOf(null))

        // When-Then
        authRepository.isUserSignedIn().test {
            assertThat(awaitItem()).isFalse()
            awaitComplete()
        }
    }

    @Test
    fun `resetPassword should emit loading, success on success`() = runTest {
        // Given
        val email = "test@example.com"

        val metaResponse = MetaResponseFactory.createMetaResponse()
        val apiResponse = RetrofitResponse.success(
            BaseApiResponse(
                Unit,
                metaResponse
            )
        )

        `when`(clientKeysNdkWrapperMock.getClientId()).thenReturn("mocked_client_id")
        `when`(clientKeysNdkWrapperMock.getClientSecret()).thenReturn("mocked_client_secret")
        `when`(authApi.resetPassword(anyOrNull())).thenReturn(apiResponse)

        // When-Then
        authRepository.resetPassword(email).test {
            assertThat(awaitItem()).isInstanceOf(Resource.Loading::class.java)
            val successResource = awaitItem()
            assertThat(successResource).isInstanceOf(Resource.Success::class.java)
            awaitComplete()
        }
    }

    @Test
    fun `resetPassword should emit loading and error on failure`() = runTest {
        val email = "test@example.com"

        val errorResponse =
            Response.error<BaseApiResponse<Unit>>(400, "client error".toResponseBody())
        val expectedApiException = HttpError(400, "Client error message")

        `when`(authApi.resetPassword(anyOrNull())).thenReturn(errorResponse)
        `when`(apiErrorHandler.handleError(anyOrNull())).thenReturn(expectedApiException)

        `when`(authApi.resetPassword(anyOrNull())).thenReturn(errorResponse)

        // When-Then
        authRepository.resetPassword(email).test {
            assertThat(awaitItem()).isInstanceOf(Resource.Loading::class.java)
            val successResource = awaitItem()
            assertThat(successResource).isInstanceOf(Resource.Error::class.java)
            awaitComplete()
        }
    }
}
