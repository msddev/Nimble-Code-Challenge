package com.mkdev.data.factory

import com.mkdev.data.datasource.local.database.room.entity.SurveyRemoteKeyEntity

object SurveyRemoteKeyEntityFactory {
    fun createSurveyRemoteKeyEntity(
        prevPage: Int? = 1,
        nextPage: Int? = 2
    ): SurveyRemoteKeyEntity {
        return SurveyRemoteKeyEntity(
            prevPage = prevPage,
            nextPage = nextPage
        )
    }

    fun createSurveyRemoteKeyEntityList(count: Int = 5): List<SurveyRemoteKeyEntity> {
        return (1..count).map {
            createSurveyRemoteKeyEntity()
        }
    }
}