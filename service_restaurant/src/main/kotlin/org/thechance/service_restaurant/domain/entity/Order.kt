package org.thechance.service_restaurant.domain.entity

data class Order(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val meals: List<OrderMeal>,
    val totalPrice: Double,
    val createdAt: Long,
    val status: Int
)
