package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable
import org.thechance.service_restaurant.domain.utils.OrderStatus

@Serializable
data class OrderDto(
    val id: String? = null,
    val userId: String? = null,
    val restaurantId: String? = null,
    val meals: List<String>? = null,
    val totalPrice: Double? = null,
    val createdAt: String? = null,
    val orderStatus: OrderStatus? = null
)