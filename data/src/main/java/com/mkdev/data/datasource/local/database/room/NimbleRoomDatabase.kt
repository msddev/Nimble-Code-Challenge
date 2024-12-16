package com.mkdev.data.datasource.local.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mkdev.data.datasource.local.database.room.dao.SurveyDao
import com.mkdev.data.datasource.local.database.room.dao.SurveyRemoteKeyDao
import com.mkdev.data.datasource.local.database.room.entity.SurveyEntity
import com.mkdev.data.datasource.local.database.room.entity.SurveyRemoteKeyEntity

@Database(
    entities = [SurveyEntity::class, SurveyRemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NimbleRoomDatabase : RoomDatabase() {
    abstract fun surveyDao(): SurveyDao
    abstract fun surveyRemoteKeyDao(): SurveyRemoteKeyDao

    companion object {
        @Volatile
        private var INSTANCE: NimbleRoomDatabase? = null

        fun getDatabase(context: Context): NimbleRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NimbleRoomDatabase::class.java,
                    "com_mkdev_nimble_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}