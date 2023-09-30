package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartDto(
    @SerialName("id") val id: String? = null,
    @SerialName("restaurantId") val restaurantId: String? = null,
    @SerialName("restaurantName") val restaurantName: String? = null,
    @SerialName("restaurantImage") val restaurantImage: String? = null,
    @SerialName("meals") val meals: List<OrderedMealDto>? = null,
    @SerialName("totalPrice") val totalPrice: Double? = null,
    @SerialName("currency") val currency: String? = null,
)