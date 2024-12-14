package com.mkdev.data.datasource.remote.model.response.singIn


import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("attributes")
    val attributes: SignInAttributesResponse,
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String
)