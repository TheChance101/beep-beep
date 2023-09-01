package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantRequestPermission(
    val id: String? = null,
    val restaurantName: String,
    val ownerEmail: String,
    val cause: String
)