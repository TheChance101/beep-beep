package data.remote.mapper

import data.remote.model.LocationDto
import domain.entity.Location

fun LocationDto.toEntity(): Location {
    return Location(latitude = latitude ?: 0.0, longitude = longitude ?: 0.0)
}

fun Location.toDto(): LocationDto {
    return LocationDto(latitude = latitude, longitude = longitude)
}