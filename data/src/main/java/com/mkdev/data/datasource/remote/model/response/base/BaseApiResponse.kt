package com.mkdev.data.datasource.remote.model.response.base

import com.google.gson.annotations.SerializedName

data class BaseApiResponse<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("meta")
    val meta: MetaResponse?
)