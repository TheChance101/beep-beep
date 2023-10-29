package org.thechance.service_restaurant.api.models

import kotlinx.serialization.SerialName
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

@Serializable
data class MealRequestDto(
    @SerialName("userId") val userId: String? = null,
    @SerialName("restaurantId") val restaurantId: String,
    @SerialName("mealId") val mealId: String,
    @SerialName("quantity") val quantity: Int,
)