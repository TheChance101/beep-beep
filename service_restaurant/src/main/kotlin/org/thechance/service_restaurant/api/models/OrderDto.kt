package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable
import org.thechance.service_restaurant.domain.entity.Order

@Serializable
data class OrderDto(
    val id: String? = null,
    val userId: String? = null,
    val restaurantId: String? = null,
    val restaurantName: String? = null,
    val restaurantImage: String? = null,
    val meals: List<OrderedMealDto>? = null,
    val totalPrice: Double? = null,
    val currency: String? = null,
    val createdAt: Long? = null,
    val orderStatus: Int? = null
)