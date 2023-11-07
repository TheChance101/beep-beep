package org.thechance.service_restaurant.domain.entity


data class Meal(
    val id: String,
    val restaurantId: String,
    val restaurantName: String,
    val name: String,
    val description: String,
    val price: Double,
    val currency: String,
    val image: String
)
