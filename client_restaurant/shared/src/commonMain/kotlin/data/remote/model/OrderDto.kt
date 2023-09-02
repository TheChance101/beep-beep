package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    @SerialName("id")
    val id: String,
    @SerialName("userId")
    val userId: String,
    @SerialName("restaurantId")
    val restaurantId: String,
    @SerialName("meals")
    val meals: List<MealDto>,
    @SerialName("totalPrice")
    val totalPrice: Double? = null,
    @SerialName("createdAt")
    val createdAt: String? = null,
    @SerialName("orderState")
    val orderState: Int
) {
    @Serializable
    data class MealDto(
        @SerialName("id")
        val id: String,
        @SerialName("mealImageUrl")
        val mealImageUrl: String,
        @SerialName("mealName")
        val mealName: String,
        @SerialName("quantity")
        val quantity: Int,
    )
}




