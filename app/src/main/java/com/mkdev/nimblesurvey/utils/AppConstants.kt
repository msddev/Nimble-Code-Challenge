package com.mkdev.nimblesurvey.utils

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