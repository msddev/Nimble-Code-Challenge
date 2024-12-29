package com.mkdev.data.datasource.remote.api

import com.mkdev.data.datasource.remote.model.request.refreshToken.RefreshTokenRequest
import com.mkdev.data.datasource.remote.model.request.resetPassword.ResetPasswordRequest
import com.mkdev.data.datasource.remote.model.request.singIn.SignInRequest
import com.mkdev.data.datasource.remote.model.response.resetPassword.ResetPasswordResponseModel
import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponseModel
import com.mkdev.data.utils.ApiConfigs
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers("${ApiConfigs.CUSTOM_HEADER}: ${ApiConfigs.NO_AUTH}")
    @POST("oauth/token")
    suspend fun signIn(@Body requestBody: SignInRequest): SignInResponseModel

    @Headers("${ApiConfigs.CUSTOM_HEADER}: ${ApiConfigs.NO_AUTH}")
    @POST("oauth/token")
    suspend fun refreshToken(@Body requestBody: RefreshTokenRequest): Response<SignInResponseModel>

    @POST("passwords")
    suspend fun resetPassword(
        @Body requestBody: ResetPasswordRequest
    ): ResetPasswordResponseModel
}