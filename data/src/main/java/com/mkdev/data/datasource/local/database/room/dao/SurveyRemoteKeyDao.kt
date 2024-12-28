package com.mkdev.data.datasource.local.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkdev.data.datasource.local.database.room.entity.SurveyRemoteKeyEntity

@Dao
interface SurveyRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<SurveyRemoteKeyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKey: SurveyRemoteKeyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: SurveyRemoteKeyEntity)

    @Query("SELECT * FROM survey_remote_key_table WHERE surveyId = :id")
    suspend fun remoteKeysId(id: Int): SurveyRemoteKeyEntity?

    @Query("DELETE FROM survey_remote_key_table")
    suspend fun clearRemoteKeys()

    @Query("SELECT * FROM survey_remote_key_table")
    suspend fun getAll(): List<SurveyRemoteKeyEntity>
}