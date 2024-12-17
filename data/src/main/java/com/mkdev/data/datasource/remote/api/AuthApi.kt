package com.mkdev.data.datasource.remote.api

import com.mkdev.data.datasource.remote.model.request.refreshToken.RefreshTokenRequest
import com.mkdev.data.datasource.remote.model.request.resetPassword.ResetPasswordRequest
import com.mkdev.data.datasource.remote.model.request.singIn.SignInRequest
import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponse
import com.mkdev.data.utils.ApiConfigs
import com.mkdev.data.datasource.remote.model.response.base.BaseApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers("${ApiConfigs.CUSTOM_HEADER}: ${ApiConfigs.NO_AUTH}")
    @POST("oauth/token")
    suspend fun signIn(@Body requestBody: SignInRequest): Response<BaseApiResponse<SignInResponse>>

    @Headers("${ApiConfigs.CUSTOM_HEADER}: ${ApiConfigs.NO_AUTH}")
    @POST("oauth/token")
    suspend fun refreshToken(@Body requestBody: RefreshTokenRequest): Response<BaseApiResponse<SignInResponse>>

    @POST("passwords")
    suspend fun resetPassword(
        @Body requestBody: ResetPasswordRequest
    ): Response<BaseApiResponse<Unit>>
}