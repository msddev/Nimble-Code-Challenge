package com.mkdev.data.repository

import com.mkdev.data.BuildConfig
import com.mkdev.data.datasource.remote.api.AuthApi
import com.mkdev.data.datasource.remote.model.request.singIn.SignInRequest
import com.mkdev.domain.entity.signIn.SignInEntity
import com.mkdev.domain.repository.AuthRepository
import com.mkdev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val authApi: AuthApi,
) : AuthRepository {

    override fun signIn(
        grantType: String,
        email: String,
        password: String,
    ): Flow<Resource<SignInEntity>> = flow {

        emit(Resource.Loading())

        try {
            val result = authApi.signIn(
                SignInRequest(
                    grantType = grantType,
                    email = email,
                    password = password,
                    clientId = BuildConfig.CLIENT_ID,
                    clientSecret = BuildConfig.CLIENT_SECRET
                )
            )

            //emit(Resource.Success(remoteSearchTripResult.trips.map { tripDtoToModelMapper.mapFrom(it) }))

        } catch (exception: Exception) {
            emit(Resource.Error(message = exception.message.orEmpty()))
        }
    }
}