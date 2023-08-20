package data.remote.remoteDto

import kotlinx.serialization.Serializable

@Serializable
data class TokensResponse(
    val accessTokenExpirationDate: Long,
    val refreshTokenExpirationDate: Long,
    val accessToken: String,
    val refreshToken: String
)
