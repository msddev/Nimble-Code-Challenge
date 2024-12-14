package com.mkdev.data.datasource.remote.api

import com.mkdev.data.datasource.remote.request.SignInRequest
import com.mkdev.data.datasource.remote.response.singIn.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {
    @POST("oauth/token")
    suspend fun signIn(@Body request: SignInRequest): SignInResponse
}