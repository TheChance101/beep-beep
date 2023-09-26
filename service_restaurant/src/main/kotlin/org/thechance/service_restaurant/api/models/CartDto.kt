package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class CartDto(
    val id: String? = null,
    val restaurantId: String? = null,
    val restaurantName: String? = null,
    val restaurantImage: String? = null,
    val meals: List<OrderedMealDto>? = null,
    val totalPrice: Double? = null,
    val currency: String? = null,
)