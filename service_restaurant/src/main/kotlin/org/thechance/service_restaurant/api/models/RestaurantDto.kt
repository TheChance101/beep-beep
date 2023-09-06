package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantDto(
    val id: String? = null,
    val ownerId: String,
    val name: String? = null,
    val description: String? = null,
    val priceLevel: String? = null,
    val rate: Double? = null,
    val currency: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val openingTime: String? = null,
    val closingTime: String? = null,
    val location: LocationDto? = null
)


