package data.remote.model

import domain.entity.UserTokens
import kotlinx.serialization.Serializable


@Serializable
data class UserTokensDto(
    val accessTokenExpirationDate: Long,
    val refreshTokenExpirationDate: Long,
    val accessToken: String,
    val refreshToken: String
)

fun UserTokensDto.toTokenEntity() = UserTokens(
    accessToken = accessToken,
    refreshToken = refreshToken
)