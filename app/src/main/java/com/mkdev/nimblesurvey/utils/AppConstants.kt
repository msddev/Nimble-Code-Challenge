package com.mkdev.nimblesurvey.utils

import com.google.crypto.tink.integration.android.AndroidKeystoreKmsClient
import kotlin.time.Duration.Companion.seconds

object ApiConfigs {
    //10 MB cache
    const val CACHE_SIZE = (10 * 1024 * 1024).toLong()

    object Timeouts {
        val connect = 10.seconds
        val write = 10.seconds
        val read = 10.seconds
    }
}

object DataConfigs {
    const val KEY_SET_NAME = "__refresh_token_encrypted_prefs_key_set__"
    const val PREF_FILE_NAME = "refresh_token_secret_prefs"
    const val MASTER_KEY_URI =
        "${AndroidKeystoreKmsClient.PREFIX}refresh_token_master_key"
    const val DATA_STORE_FINE_NAME = "user_local"
}