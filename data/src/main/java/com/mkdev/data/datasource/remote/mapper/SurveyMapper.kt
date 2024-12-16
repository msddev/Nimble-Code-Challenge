package com.mkdev.data.datasource.remote.mapper

import com.mkdev.data.datasource.remote.model.response.survey.SurveyResponse
import com.mkdev.domain.entity.survey.SurveyEntity
import javax.inject.Inject

class SurveyMapper @Inject constructor() {
    fun mapToSurveyEntity(surveyResponse: SurveyResponse): SurveyEntity {
        return SurveyEntity(
            id = surveyResponse.id,
            title = surveyResponse.attributes.title,
            description = surveyResponse.attributes.description,
            coverImageUrl = surveyResponse.attributes.coverImageUrl,
            isActive = surveyResponse.attributes.isActive,
            surveyType = surveyResponse.attributes.surveyType
        )
    }
}