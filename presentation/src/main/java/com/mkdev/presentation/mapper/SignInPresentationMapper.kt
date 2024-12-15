package com.mkdev.presentation.mapper

import com.mkdev.domain.entity.signIn.SignInEntity
import com.mkdev.presentation.model.signIn.SignInModel

fun SignInEntity.toSignInModel(): SignInModel {
    return SignInModel(
        accessToken = this.accessToken,
        createdAt = this.createdAt,
        expiresIn = this.expiresIn,
        refreshToken = this.refreshToken,
        tokenType = this.tokenType
    )
}