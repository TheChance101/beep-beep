package org.thechance.common.data.remote.model

import kotlinx.serialization.Serializable


@Serializable
data class UserTokensRemoteDto(
    val accessTokenExpirationDate: Long,
    val refreshTokenExpirationDate: Long,
    val accessToken: String,
    val refreshToken: String
)