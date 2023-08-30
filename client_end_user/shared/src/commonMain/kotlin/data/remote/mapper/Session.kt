package data.remote.mapper

import data.remote.model.SessionDto
import domain.entity.Session

fun SessionDto.toTokenEntity() = Session(
    accessToken = accessToken,
    refreshToken = refreshToken,
    accessTokenExpirationDate = accessTokenExpirationDate,
    refreshTokenExpirationDate = refreshTokenExpirationDate,
)