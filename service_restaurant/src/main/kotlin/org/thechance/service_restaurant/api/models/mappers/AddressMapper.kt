package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.AddressDto
import org.thechance.service_restaurant.entity.Address

fun Address.toDto(): AddressDto {
    return AddressDto(
        id = id,
        lat = lat,
        lon = lon,
    )
}

fun List<Address>.toDto(): List<AddressDto> = map { it.toDto() }