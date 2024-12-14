package com.mkdev.presentation.common.utils

import androidx.core.util.PatternsCompat

fun String.isValidEmail(): Boolean {
    val pattern = PatternsCompat.EMAIL_ADDRESS
    return pattern.matcher(this).matches()
}

/**
 * At least one lowercase letter
 * At least one uppercase letter
 * At least one digit
 * At least one special character from the set [@\$!%*?&#]
 */
fun String.isValidPassword(): Boolean {
    val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&#])[A-Za-z\\d@\$!%*?&#]{8,}\$")
    return this.matches(passwordRegex)
}