package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.AddressDto
import org.thechance.service_restaurant.domain.entity.Address

fun Address.toDto(): AddressDto {
    return AddressDto(
        id = id,
        latitude = latitude,
        longitude = longitude,
    )
}

fun AddressDto.toEntity() = Address(
    id = id ?: "",
    latitude = latitude ?: -1.0,
    longitude = longitude ?: -1.0,
)


fun List<Address>.toDto(): List<AddressDto> = map { it.toDto() }