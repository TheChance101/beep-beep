package org.thechance.api_gateway.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserTokens(
    val accessTokenExpirationDate: Long,
    val refreshTokenExpirationDate: Long,
    val accessToken: String,
    val refreshToken: String
)