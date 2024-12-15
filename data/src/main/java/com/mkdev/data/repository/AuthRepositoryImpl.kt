package com.mkdev.data.repository

import com.mkdev.data.BuildConfig
import com.mkdev.data.datasource.local.dataStore.UserLocalSource
import com.mkdev.data.datasource.remote.api.AuthApi
import com.mkdev.data.datasource.remote.mapper.toUserLocal
import com.mkdev.data.datasource.remote.model.request.singIn.SignInRequest
import com.mkdev.data.utils.ApiErrorHandler
import com.mkdev.domain.repository.AuthRepository
import com.mkdev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val userLocalSource: UserLocalSource,
    private val authApi: AuthApi,
    private val apiErrorHandler: ApiErrorHandler,
) : AuthRepository {

    override fun signIn(
        grantType: String,
        email: String,
        password: String,
    ): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading())

        runCatching {
            authApi.signIn(
                SignInRequest(
                    grantType = grantType,
                    email = email,
                    password = password,
                    clientId = BuildConfig.CLIENT_ID,
                    clientSecret = BuildConfig.CLIENT_SECRET
                )
            )
        }.onSuccess { result ->
            if (result.isSuccessful) {
                userLocalSource.update { result.body()?.data?.toUserLocal() }
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
}