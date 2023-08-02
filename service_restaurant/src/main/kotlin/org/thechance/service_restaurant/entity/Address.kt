package org.thechance.service_restaurant.entity

import org.thechance.service_restaurant.api.models.AddressDto
import org.thechance.service_restaurant.data.collection.AddressCollection

data class Address(
    val id: String,
    val lat: Long? = null,
    val lon: Long? = null,
    val isDeleted: Boolean = false
) {
    fun toDto(): AddressDto {
        return AddressDto(
            id = id,
            lat = lat,
            lon = lon,
        )
    }

    fun toCollection(): AddressCollection {
        return AddressCollection(
            lat = lat,
            lon = lon,
            isDeleted = isDeleted,
        )
    }
}

fun List<Address>.toDto(): List<AddressDto> = map { it.toDto() }