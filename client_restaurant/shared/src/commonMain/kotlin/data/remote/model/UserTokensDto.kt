package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserTokensDto(
    @SerialName("accessTokenExpirationDate")
    val accessTokenExpirationDate: Long,
    @SerialName("refreshTokenExpirationDate")
    val refreshTokenExpirationDate: Long,
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
)

