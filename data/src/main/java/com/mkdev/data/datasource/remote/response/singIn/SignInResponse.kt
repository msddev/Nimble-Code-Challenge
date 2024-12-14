package com.mkdev.data.datasource.remote.response.singIn


import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("data")
    val singInData: SignInDataResponse
)