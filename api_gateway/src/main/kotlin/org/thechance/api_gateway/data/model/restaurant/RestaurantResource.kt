package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantResource(
    val id: String? = null,
    val ownerId: String,
    val name: String? = null,
    val description: String? = null,
    val priceLevel: String? = null,
    val rate: Double? = null,
    val phone: String? = null,
    val openingTime: String? = null,
    val closingTime: String? = null,
    val address: String? = null,
    val location: LocationResource? = null,
)