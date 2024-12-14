package com.mkdev.data.datasource.local.dataStore

import android.util.Log
import androidx.datastore.core.DataStore
import com.mkdev.data.datasource.local.UserLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class UserLocalSourceImpl @Inject constructor(
    private val dataStore: DataStore<UserLocal>,
) : UserLocalSource {
    override fun user() = dataStore.data
        .onEach { Log.d("USER_LOCAL", "userLocal=$it") }
        .map { v -> v.takeIf { it != USER_LOCAL_NULL } }
        .catch { cause: Throwable ->
            if (cause is IOException) {
                emit(null)
            } else {
                throw cause
            }
        }
        .flowOn(Dispatchers.IO)

    override suspend fun update(transform: suspend (current: UserLocal?) -> UserLocal?) =
        withContext(Dispatchers.IO) {
            dataStore.updateData { current ->
                transform(current.takeIf { it != USER_LOCAL_NULL }) ?: USER_LOCAL_NULL
            }
        }
}
