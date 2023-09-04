package domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantRequestPermission(
    val restaurantName: String,
    val ownerEmail: String,
    val cause: String
)