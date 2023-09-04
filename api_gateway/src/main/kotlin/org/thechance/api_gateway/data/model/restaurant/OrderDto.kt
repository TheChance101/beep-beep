package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    @SerialName("id") val id: String,
    @SerialName("userId") val userId: String,
    @SerialName("restaurantId") val restaurantId: String,
    @SerialName("meals") val meals: List<Meal>,
    @SerialName("totalPrice") val totalPrice: Double,
    @SerialName("createdAt") val createdAt: Long,
    @SerialName("orderStatus") val orderStatus: Int = 0,
) {
    @Serializable
    data class Meal(
        @SerialName("mealId") val mealId: String,
        @SerialName("quantity") val quantity: Int
    )
}