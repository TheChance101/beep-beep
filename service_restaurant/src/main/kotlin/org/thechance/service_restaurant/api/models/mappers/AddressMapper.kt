package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.AddressDto
import org.thechance.service_restaurant.domain.entity.Location

fun Location.toDto(): AddressDto {
    return AddressDto(
        latitude = latitude,
        longitude = longitude,
    )
}

fun AddressDto?.toEntity() = Location(
    latitude = this?.latitude ?: -1.0,
    longitude = this?.longitude ?: -1.0,
)


