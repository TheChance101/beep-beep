package org.thechance.common.domain.entity

data class UserTokens(
    val accessToken: String,
    val refreshToken: String
)