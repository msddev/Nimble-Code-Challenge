package com.mkdev.data.factory

import com.mkdev.data.datasource.remote.model.response.survey.SurveyAttributesResponse
import com.mkdev.data.datasource.remote.model.response.survey.SurveyResponse

object SurveyResponseFactory {
    fun createSurveyResponse(
        id: String = "survey_id",
        title: String = "Survey Title",
        description: String = "Survey Description",
        coverImageUrl: String = "https://example.com/image.jpg",
        isActive: Boolean = true,
        surveyType: String = "customer_satisfaction"
    ): SurveyResponse {
        return SurveyResponse(
            attributes = SurveyAttributesResponse(
                activeAt = "",
                coverImageUrl = coverImageUrl,
                createdAt = "",
                description = description,
                inactiveAt = Any(),
                isActive = isActive,
                surveyType = surveyType,
                thankEmailAboveThreshold = "",
                thankEmailBelowThreshold = "",
                title = title
            ),
            id = id,
            type = ""
        )
    }

    fun createSurveyResponseList(count: Int = 5): List<SurveyResponse> {
        return (1..count).map {
            createSurveyResponse(id = "survey_id_$it")
        }
    }
}