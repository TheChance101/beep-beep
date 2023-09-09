package data.remote.mapper

import data.remote.model.SessionDto
import domain.entity.Session

fun SessionDto.toSessionEntity() = Session(
    accessToken = accessToken,
    refreshToken = refreshToken,
    accessTokenExpirationDate = accessTokenExpirationDate,
    refreshTokenExpirationDate = refreshTokenExpirationDate,
)