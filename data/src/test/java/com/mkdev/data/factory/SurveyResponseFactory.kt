package com.mkdev.data.factory

import com.infinum.jsonapix.core.resources.DefaultError
import com.mkdev.data.datasource.remote.model.response.survey.SurveyResponse
import com.mkdev.data.datasource.remote.model.response.survey.SurveyResponseItem
import com.mkdev.data.datasource.remote.model.response.survey.SurveyResponseList

object SurveyResponseFactory {
    fun createSurveyResponse(
        title: String = "Survey Title",
        description: String = "Survey Description",
        coverImageUrl: String = "https://example.com/image.jpg",
        isActive: Boolean = true,
        surveyType: String = "customer_satisfaction"
    ): SurveyResponseItem {
        val surveyResponseItem = SurveyResponse(
            activeAt = "",
            coverImageUrl = coverImageUrl,
            createdAt = "",
            description = description,
            inactiveAt = "",
            isActive = isActive,
            surveyType = surveyType,
            thankEmailAboveThreshold = "",
            thankEmailBelowThreshold = "",
            title = title
        )

        return SurveyResponseItem(data = surveyResponseItem)
    }

    fun createSurveyResponseList(count: Int = 5): SurveyResponseList {
        val surveyResponses = mutableListOf<SurveyResponseItem>()

        for (i in 1..count) {
            surveyResponses.add(createSurveyResponse())
        }
        return SurveyResponseList(data = surveyResponses)
    }

    fun createSurveyErrorResponse(): SurveyResponseList {
        val surveyResponses = mutableListOf<SurveyResponseItem>()
        surveyResponses.add(createSurveyResponse())
        return SurveyResponseList(
            data = emptyList(),
            errors = listOf(DefaultError(code = "", title = "", detail = "", status = ""))
        )
    }
}