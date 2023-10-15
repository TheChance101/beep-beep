package data.remote.mapper

import data.remote.model.LocationDto
import domain.entity.Location

fun LocationDto.toEntity() = Location(latitude = latitude ?: 0.0, longitude = longitude ?: 0.0)
