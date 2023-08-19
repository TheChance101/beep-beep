package data.mapper

import data.remote.remoteDto.TokensResponse
import domain.entity.Tokens

fun TokensResponse.toEntity() = Tokens(accessToken, refreshToken)