package com.mkdev.data.datasource.remote.model.response.singIn


import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("data")
    val singInData: SignInDataResponse
)