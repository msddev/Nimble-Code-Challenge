package com.mkdev.data.utils

import com.google.crypto.tink.integration.android.AndroidKeystoreKmsClient

internal object RemoteApiPaging {
    const val PAGE_SIZE = 5
    const val FIRST_PAGE = 1
}

internal object ApiConfigs {
    const val CUSTOM_HEADER = "@"
    const val NO_AUTH = "NoAuth"
}

object DataConfigs {
    const val KEY_SET_NAME = "__refresh_token_encrypted_prefs_key_set__"
    const val PREF_FILE_NAME = "refresh_token_secret_prefs"
    const val MASTER_KEY_URI =
        "${AndroidKeystoreKmsClient.PREFIX}refresh_token_master_key"
    const val DATA_STORE_FINE_NAME = "user_local"
}