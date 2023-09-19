package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    @SerialName("id") val id: String? = null,
    @SerialName("userId") val userId: String? = null,
    @SerialName("restaurantId") val restaurantId: String? = null,
    @SerialName("meals") val meals: List<Meal>? = null,
    @SerialName("totalPrice") val totalPrice: Double? = null,
    @SerialName("createdAt") val createdAt: Long? = null,
    @SerialName("orderStatus") val orderStatus: Int = 0,
) {
    @Serializable
    data class Meal(
        @SerialName("mealId") val mealId: String,
        @SerialName("quantity") val quantity: Int
    )
}