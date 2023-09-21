package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    @SerialName("id") val id: String,
    @SerialName("userId") val userId: String,
    @SerialName("restaurantId") val restaurantId: String,
    @SerialName("restaurantName") val restaurantName: String,
    @SerialName("restaurantImage") val restaurantImageUrl: String? = null,
    @SerialName("meals") val meals: List<MealDto>,
    @SerialName("totalPrice") val totalPrice: Double? = null,
    @SerialName("createdAt") val createdAt: Long? = null,
    @SerialName("orderStatus") val orderStatus: Int = 0,
    @SerialName("timeToArriveInMints") val timeToArriveInMints: Int? = null,
) {
    @Serializable
    data class MealDto(
        @SerialName("mealId") val mealId: String,
        @SerialName("mealName") val mealName: String,
        @SerialName("quantity") val quantity: Int
    )
}