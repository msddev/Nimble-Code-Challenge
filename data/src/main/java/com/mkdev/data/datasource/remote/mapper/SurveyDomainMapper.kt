package com.mkdev.data.datasource.remote.mapper

import com.mkdev.data.datasource.local.database.room.entity.SurveyEntity
import com.mkdev.domain.model.survey.SurveyModel
import javax.inject.Inject

class SurveyDomainMapper @Inject constructor() {
    fun mapToSurveyDomainModel(surveyEntity: SurveyEntity): SurveyModel {
        return SurveyModel(
            title = surveyEntity.title,
            description = surveyEntity.description,
            coverImageUrl = surveyEntity.coverImageUrl,
            isActive = surveyEntity.isActive,
            surveyType = surveyEntity.surveyType
        )
    }
}