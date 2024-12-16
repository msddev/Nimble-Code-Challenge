package com.mkdev.data.datasource.remote.model.response.survey


import com.google.gson.annotations.SerializedName

data class SurveyResponse(
    @SerializedName("attributes")
    val attributes: SurveyAttributesResponse,
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String
)