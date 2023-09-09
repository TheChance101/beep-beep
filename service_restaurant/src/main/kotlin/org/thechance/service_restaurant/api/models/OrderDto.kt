package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    val id: String? = null,
    val userId: String? = null,
    val restaurantId: String? = null,
    val meals: List<MealDto>? = null,
    val totalPrice: Double? = null,
    val createdAt: Long? = null,
    val orderStatus: Int = 0
){
    @Serializable
    data class MealDto(
        val mealId: String,
        val quantity: Int
    )
}