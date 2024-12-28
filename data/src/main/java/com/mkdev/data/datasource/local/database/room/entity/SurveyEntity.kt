package com.mkdev.data.datasource.local.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "survey_table")
data class SurveyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val coverImageUrl: String,
    val isActive: Boolean,
    val surveyType: String,
)
