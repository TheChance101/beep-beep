package org.thechance.service_restaurant.domain.entity

data class OrderHistory(
    val id: String,
    val restaurantId: String,
    val restaurantName: String,
    val restaurantImage: String,
    val meals: List<OrderedMeal>,
    val totalPrice: Double,
    val createdAt: Long,
    val status: Int
)
