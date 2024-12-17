package com.mkdev.data.factory

import com.mkdev.data.datasource.local.database.room.entity.SurveyEntity

object SurveyEntityFactory {

    fun createSurveyEntity(
        id: String = "survey_id",
        title: String = "Survey Title",
        description: String = "Survey Description",
        coverImageUrl: String = "https://example.com/image.jpg",
        isActive: Boolean = true,
        surveyType: String = "customer_satisfaction"
    ): SurveyEntity {
        return SurveyEntity(
            id = id,
            title = title,
            description = description,
            coverImageUrl = coverImageUrl,
            isActive = isActive,
            surveyType = surveyType
        )
    }

    fun createSurveyEntityList(count: Int = 5): List<SurveyEntity> {
        return (1..count).map {
            createSurveyEntity(id = "survey_id_$it")
        }
    }
}