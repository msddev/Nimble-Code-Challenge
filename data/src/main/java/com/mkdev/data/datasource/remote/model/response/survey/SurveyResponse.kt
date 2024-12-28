package com.mkdev.data.datasource.remote.model.response.survey

import com.infinum.jsonapix.annotations.JsonApiX
import com.infinum.jsonapix.annotations.JsonApiXMeta
import com.infinum.jsonapix.core.JsonApiModel
import com.infinum.jsonapix.core.resources.Meta
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("survey_simple")
@JsonApiX("survey_simple")
data class SurveyResponse(
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("thank_email_above_threshold")
    val thankEmailAboveThreshold: String? = null,
    @SerialName("thank_email_below_threshold")
    val thankEmailBelowThreshold: String? = null,
    @SerialName("is_active")
    val isActive: Boolean,
    @SerialName("cover_image_url")
    val coverImageUrl: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("active_at")
    val activeAt: String,
    @SerialName("inactive_at")
    val inactiveAt: String? = null,
    @SerialName("survey_type")
    val surveyType: String,
) : JsonApiModel()

@SerialName("survey_simple")
@Serializable
@JsonApiXMeta("survey_simple")
data class SurveyMetaResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("pages")
    val pages: Int,
    @SerialName("page_size")
    val pageSize: Int,
    @SerialName("records")
    val records: Int,
) : Meta