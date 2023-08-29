package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class UserTokensDto(
    val accessTokenExpirationDate: Long,
    val refreshTokenExpirationDate: Long,
    val accessToken: String,
    val refreshToken: String
)
