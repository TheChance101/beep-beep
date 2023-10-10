package org.thechance.api_gateway.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealRequestDto(
    @SerialName("userId") val userId: String? = null,
    @SerialName("restaurantId") val restaurantId: String,
    @SerialName("mealId") val mealId: String,
    @SerialName("quantity") val quantity: Int,
)