package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class UserTokenDto(
    val userId: String,
    val refreshToken: String,
    val accessToken: String,
    val expiresIn: Int,
)
