package com.mkdev.data.datasource.local.database.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkdev.data.datasource.local.database.room.entity.SurveyEntity

@Dao
interface SurveyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(surveys: List<SurveyEntity>)

    @Query("SELECT * FROM survey_table")
    fun getByPaging(): PagingSource<Int, SurveyEntity>

    @Query("SELECT * FROM survey_table WHERE id =:id LIMIT 1")
    suspend fun getById(id: String): SurveyEntity

    @Query("DELETE FROM survey_table")
    suspend fun clearAll()
}