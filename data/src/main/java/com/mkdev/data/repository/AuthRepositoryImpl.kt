package com.mkdev.data.repository

import com.mkdev.data.BuildConfig
import com.mkdev.data.datasource.local.datastore.UserLocalSource
import com.mkdev.data.datasource.local.mapper.SignInMapper
import com.mkdev.data.datasource.remote.api.AuthApi
import com.mkdev.data.datasource.remote.model.request.resetPassword.ResetPasswordRequest
import com.mkdev.data.datasource.remote.model.request.resetPassword.UserRequest
import com.mkdev.data.datasource.remote.model.request.singIn.SignInRequest
import com.mkdev.data.utils.ApiErrorHandler
import com.mkdev.domain.repository.AuthRepository
import com.mkdev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val userLocalSource: UserLocalSource,
    private val authApi: AuthApi,
    private val apiErrorHandler: ApiErrorHandler,
    private val signInMapper: SignInMapper,
) : AuthRepository {

    override fun signIn(
        grantType: String,
        email: String,
        password: String,
    ): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading())

        runCatching {
            authApi.signIn(
                requestBody = SignInRequest(
                    grantType = grantType,
                    email = email,
                    password = password,
                    clientId = BuildConfig.CLIENT_ID,
                    clientSecret = BuildConfig.CLIENT_SECRET
                )
            )
        }.onSuccess { result ->
            if (result.isSuccessful) {
                userLocalSource.update { signInMapper.mapToUserLocal(result.body()?.data) }
                emit(Resource.Success(Unit))
            } else {
                val apiException = apiErrorHandler.handleError(HttpException(result))
                emit(Resource.Error(apiException.message))
            }
        }.onFailure { throwable ->
            val apiException = apiErrorHandler.handleError(throwable)
            emit(Resource.Error(apiException.message))
        }
    }

    override fun signOut(): Flow<Resource<Unit>> = flow {
        userLocalSource.update { null }
        emit(Resource.Success(Unit))
    }

    override fun isUserSignedIn(): Flow<Boolean> {
        return userLocalSource.user().map { userLocal ->
            userLocal != null
        }.distinctUntilChanged()
    }

    override fun resetPassword(email: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        runCatching {
            authApi.resetPassword(
                requestBody = ResetPasswordRequest(
                    user = UserRequest(email = email),
                    clientId = BuildConfig.CLIENT_ID,
                    clientSecret = BuildConfig.CLIENT_SECRET
                )
            )
        }.onSuccess { result ->
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()?.meta?.message))
            } else {
                val apiException = apiErrorHandler.handleError(HttpException(result))
                emit(Resource.Error(apiException.message))
            }
        }.onFailure { throwable ->
            val apiException = apiErrorHandler.handleError(throwable)
            emit(Resource.Error(apiException.message))
        }
    }
}