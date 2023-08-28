package org.thechance.service_restaurant.domain.entity

data class RestaurantPermissionRequest(
    val id: String,
    val restaurantName: String,
    val ownerEmail: String,
    val cause: String,
)
