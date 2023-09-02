package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.LocationDto
import org.thechance.service_restaurant.domain.entity.Location

fun Location.toDto(): LocationDto {
    return LocationDto(
        latitude = latitude,
        longitude = longitude,
    )
}

fun LocationDto?.toEntity() = Location(
    latitude = this?.latitude ?: -1.0,
    longitude = this?.longitude ?: -1.0,
)


