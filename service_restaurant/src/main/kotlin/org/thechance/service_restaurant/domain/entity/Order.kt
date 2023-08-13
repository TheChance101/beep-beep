package org.thechance.service_restaurant.domain.entity

import kotlinx.datetime.LocalDateTime
import org.thechance.service_restaurant.domain.utils.OrderStatus

data class Order(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val meals: List<Meal>,
    val totalPrice: Double,
    val createdAt: LocalDateTime,
    val orderStatus: OrderStatus
)

