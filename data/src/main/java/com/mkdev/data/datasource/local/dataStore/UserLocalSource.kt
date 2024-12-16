package com.mkdev.data.datasource.local.dataStore

import com.mkdev.data.datasource.local.UserLocal
import kotlinx.coroutines.flow.Flow

interface UserLocalSource {
    fun user(): Flow<UserLocal?>

    suspend fun update(transform: suspend (current: UserLocal?) -> UserLocal?): UserLocal?
}

@JvmField
internal val USER_LOCAL_NULL: UserLocal = UserLocal.getDefaultInstance()
