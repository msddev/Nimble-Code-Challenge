package com.mkdev.data.datasource.remote.model.response.base

import com.google.gson.annotations.SerializedName

data class MetaResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("pages")
    val pages: Int?,
    @SerializedName("page_size")
    val pageSize: Int?,
    @SerializedName("records")
    val records: Int?,
    @SerializedName("message")
    val message: String?,
)
