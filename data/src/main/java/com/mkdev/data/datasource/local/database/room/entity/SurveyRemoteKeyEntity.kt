package com.mkdev.data.datasource.local.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "survey_remote_key_table")
data class SurveyRemoteKeyEntity(
    @PrimaryKey(autoGenerate = true)
    val surveyId: Int = 0,
    val prevPage: Int?,
    val nextPage: Int?
)