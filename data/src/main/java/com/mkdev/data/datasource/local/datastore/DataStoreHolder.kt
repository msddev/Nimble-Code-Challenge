package com.mkdev.data.datasource.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import com.mkdev.data.datasource.local.UserLocal
import com.mkdev.data.utils.DataConfigs

object DataStoreHolder {
    private lateinit var INSTANCE: DataStore<UserLocal>

    fun getInstance(
        applicationContext: Context,
        serializer: Serializer<UserLocal>
    ): DataStore<UserLocal> {
        if (!::INSTANCE.isInitialized) {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = DataStoreFactory.create(
                        serializer = serializer,
                        produceFile = { applicationContext.dataStoreFile(DataConfigs.DATA_STORE_FINE_NAME) }
                    )
                }
            }
        }
        return INSTANCE
    }
}