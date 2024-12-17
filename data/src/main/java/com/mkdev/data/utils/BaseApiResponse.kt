package com.mkdev.data.utils

import com.google.gson.annotations.SerializedName
import com.mkdev.data.datasource.remote.model.response.base.MetaResponse


data class BaseApiResponse<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("meta")
    val meta: MetaResponse?
)