package org.thechance.common.data.remote.model

data class RestaurantDto(
    val id: String,
    val restaurantName: String,
    val ownerUsername: String,
    val phoneNumber: String,
    val rating: Double,
    val priceLevel: Int,
    val workingHours: String,
)
