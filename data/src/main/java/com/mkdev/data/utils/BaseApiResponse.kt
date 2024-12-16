package com.mkdev.data.utils

import com.google.gson.annotations.SerializedName


data class BaseApiResponse<T>(
    @SerializedName("data")
    val data: T?
)