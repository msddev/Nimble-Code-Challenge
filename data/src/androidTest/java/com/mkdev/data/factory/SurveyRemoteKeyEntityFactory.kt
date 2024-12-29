package com.mkdev.data.factory

import com.mkdev.data.datasource.local.database.room.entity.SurveyRemoteKeyEntity

object SurveyRemoteKeyEntityFactory {
    fun createSurveyRemoteKeyEntity(
        surveyId: Int = 1,
        prevPage: Int? = 1,
        nextPage: Int? = 2
    ): SurveyRemoteKeyEntity {
        return SurveyRemoteKeyEntity(
            surveyId = surveyId,
            prevPage = prevPage,
            nextPage = nextPage
        )
    }

    fun createSurveyRemoteKeyEntityList(count: Int = 5): List<SurveyRemoteKeyEntity> {
        return (1..count).map {
            createSurveyRemoteKeyEntity(surveyId = it)
        }
    }
}