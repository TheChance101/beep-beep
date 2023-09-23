package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderHistoryDto(
    val id: String? = null,
    val userId: String? = null,
    val restaurantId: String? = null,
    val restaurantName: String? = null,
    val restaurantImage: String? = null,
    val meals: List<MealDto>? = null,
    val totalPrice: Double? = null,
    val createdAt: Long? = null,
    val status: Int? = null
) {
    @Serializable
    data class MealDto(
        val meadId: String,
//        val mealName: String,
        val quantity: Int
    )
}