package com.mkdev.data.datasource.remote.response.singIn


import com.google.gson.annotations.SerializedName

data class SignInDataResponse(
    @SerializedName("attributes")
    val attributes: SignInAttributesResponse,
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String
)