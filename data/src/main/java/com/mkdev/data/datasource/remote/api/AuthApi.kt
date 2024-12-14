package com.mkdev.data.datasource.remote.api

import com.mkdev.data.datasource.remote.model.request.singIn.SignInRequest
import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("oauth/token")
    suspend fun signIn(@Body request: SignInRequest): SignInResponse
}