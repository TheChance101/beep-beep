package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationDate: Long,
    val refreshTokenExpirationDate: Long,
)
