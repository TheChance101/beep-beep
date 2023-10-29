package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodOrderDto(
    @SerialName("id") val id: String? = null,
    @SerialName("userId") val userId: String? = null,
    @SerialName("restaurantId") val restaurantId: String? = null,
    @SerialName("restaurantName") val restaurantName: String? = null,
    @SerialName("restaurantImage") val restaurantImage: String? = null,
    @SerialName("currency") val currency: String? = null,
    @SerialName("meals") val meals: List<CartMealDto>? = null,
    @SerialName("totalPrice") val totalPrice: Double? = null,
    @SerialName("createdAt") val createdAt: Long? = null,
    @SerialName("orderStatus") val orderStatus: Int = 0,
)