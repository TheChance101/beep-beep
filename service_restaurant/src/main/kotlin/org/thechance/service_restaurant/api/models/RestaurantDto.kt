package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantDto(
    val id: String? = null,
    val ownerId: String,
    val name: String,
    val description: String,
    val priceLevel: String,
    val rate: Double,
    val phone: String,
    val openingTime: String,
    val closingTime: String,
)


