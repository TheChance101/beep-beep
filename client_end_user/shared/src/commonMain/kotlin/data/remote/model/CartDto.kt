package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartDto(
    @SerialName("meals") val meals: List<CartMealDto>? = null,
    @SerialName("totalPrice") val totalPrice: Double? = null,
    @SerialName("currency") val currency: String? = null
)