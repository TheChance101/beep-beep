package org.thechance.api_gateway.data.security

data class TokenConfiguration(
    val secret: String,
    val issuer: String,
    val audience: String,
    val accessTokenExpirationTimestamp: Long,
    val refreshTokenExpirationTimestamp: Long
)