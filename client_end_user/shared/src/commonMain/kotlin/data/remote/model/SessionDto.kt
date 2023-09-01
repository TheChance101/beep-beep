package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("accessTokenExpirationDate")
    val accessTokenExpirationDate: Long,
    @SerialName("refreshTokenExpirationDate")
    val refreshTokenExpirationDate: Long,
)
