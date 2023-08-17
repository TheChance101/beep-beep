package org.thechance.api_gateway.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class TokensDto(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationDate: String,
    val refreshTokenExpirationDate: String
)