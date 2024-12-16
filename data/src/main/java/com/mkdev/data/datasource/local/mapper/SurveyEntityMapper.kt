package com.mkdev.data.datasource.local.mapper

import com.mkdev.data.datasource.local.database.room.entity.SurveyEntity
import com.mkdev.data.datasource.remote.model.response.survey.SurveyResponse
import javax.inject.Inject

class SurveyEntityMapper @Inject constructor() {
    fun mapToSurveyEntity(surveyResponse: SurveyResponse): SurveyEntity {
        return with(surveyResponse.attributes) {
            SurveyEntity(
                id = surveyResponse.id,
                title = title,
                description = description,
                coverImageUrl = coverImageUrl,
                isActive = isActive,
                surveyType = surveyType
            )
        }
    }
}