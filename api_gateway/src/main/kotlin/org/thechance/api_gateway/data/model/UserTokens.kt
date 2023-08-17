package org.thechance.api_gateway.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserTokens(
    val accessTokenExpirationDate: Long,
    val refreshTokenExpirationDate: Long,
    val accessToken: String,
    val refreshToken: String
)