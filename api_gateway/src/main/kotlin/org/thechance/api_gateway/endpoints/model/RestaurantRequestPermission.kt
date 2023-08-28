package org.thechance.api_gateway.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantRequestPermission(
    val id: String,
    val restaurantName: String,
    val ownerEmail: String,
    val cause: String
)