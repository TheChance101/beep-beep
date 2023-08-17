package org.thechance.api_gateway.data.mappers

import org.thechance.api_gateway.domain.entity.UserTokens
import org.thechance.api_gateway.endpoints.model.TokensDto
import java.util.*

fun UserTokens.toDto() = TokensDto(
    accessToken = accessToken,
    refreshToken = refreshToken,
    accessTokenExpirationDate = Date(accessTokenExpirationDate).toString(),
    refreshTokenExpirationDate = Date(refreshTokenExpirationDate).toString()
)