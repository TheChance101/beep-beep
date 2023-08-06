package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    val latitude: Double? = null,
    val longitude: Double? = null,
)

