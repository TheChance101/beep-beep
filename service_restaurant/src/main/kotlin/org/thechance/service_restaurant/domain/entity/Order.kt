package org.thechance.service_restaurant.domain.entity

import kotlinx.datetime.LocalDateTime

data class Order(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val meals: List<OrderMeal>,
    val totalPrice: Double,
    val createdAt: LocalDateTime,
    val status: Int
)
