package data.local.mapper

import data.remote.model.UserTokensDto
import domain.entity.UserTokens


fun UserTokensDto.toEntity() = UserTokens(
    accessToken = accessToken,
    refreshToken = refreshToken
)