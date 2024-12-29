package com.mkdev.data.datasource.remote.model.response.resetPassword

import com.infinum.jsonapix.annotations.JsonApiX
import com.infinum.jsonapix.annotations.JsonApiXMeta
import com.infinum.jsonapix.core.JsonApiModel
import com.infinum.jsonapix.core.resources.Meta
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@JsonApiX("resetPassword", isNullable = true)
class ResetPasswordResponse : JsonApiModel()

@Serializable
@JsonApiXMeta("resetPassword")
data class ResetPasswordMetaResponse(
    @SerialName("message")
    val message: String
) : Meta
