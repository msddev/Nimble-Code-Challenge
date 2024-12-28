package com.mkdev.data.datasource.remote.model.response.resetPassword

import com.infinum.jsonapix.annotations.JsonApiX
import com.infinum.jsonapix.annotations.JsonApiXMeta
import com.infinum.jsonapix.core.JsonApiModel
import com.infinum.jsonapix.core.resources.Meta
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("resetPassword")
@JsonApiX("resetPassword")
@Serializable
data class ResetPasswordResponse(val test: String? = null) : JsonApiModel()

@SerialName("resetPassword")
@Serializable
@JsonApiXMeta("resetPassword")
data class ResetPasswordMetaResponse(
    val message: String
) : Meta