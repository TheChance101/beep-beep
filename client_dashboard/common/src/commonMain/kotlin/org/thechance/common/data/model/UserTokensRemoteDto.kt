package org.thechance.common.data.model

import kotlinx.serialization.Serializable


@Serializable
data class UserTokensRemoteDto(
    val accessTokenExpirationDate: Long? = null,
    val refreshTokenExpirationDate: Long? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null
)