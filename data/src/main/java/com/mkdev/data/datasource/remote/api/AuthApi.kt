package com.mkdev.data.datasource.remote.api


import com.mkdev.data.datasource.remote.model.request.refreshToken.RefreshTokenRequest
import com.mkdev.data.datasource.remote.model.request.singIn.SignInRequest
import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponse
import com.mkdev.data.utils.ApiConfigs
import com.mkdev.data.utils.BaseApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers("${ApiConfigs.CUSTOM_HEADER}: ${ApiConfigs.NO_AUTH}")
    @POST("oauth/token")
    suspend fun signIn(@Body request: SignInRequest): Response<BaseApiResponse<SignInResponse>>

    @Headers("${ApiConfigs.CUSTOM_HEADER}: ${ApiConfigs.NO_AUTH}")
    @POST("oauth/token")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<BaseApiResponse<SignInResponse>>
}