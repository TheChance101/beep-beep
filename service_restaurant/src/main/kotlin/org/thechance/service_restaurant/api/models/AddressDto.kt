package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable
import org.thechance.service_restaurant.domain.entity.Address

@Serializable
data class AddressDto(
    val id: String = "",
    val lat: Long? = null,
    val lon: Long? = null,
) {
    fun toEntity(): Address {
        return Address(
            id = id,
            lat = lat,
            lon = lon,
        )
    }
}