package data.remote.mapper

import data.remote.model.SessionDto
import domain.entity.Session

fun SessionDto.toEntity() = Session(
    accessToken = accessToken,
    refreshToken = refreshToken,
)