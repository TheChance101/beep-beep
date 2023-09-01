package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    @SerialName("id")
    val id: String,
    @SerialName("id")
    val userId: String,
    @SerialName("id")
    val restaurantId: String,
    @SerialName("id")
    val meals: List<MealDto>,
    @SerialName("id")
    val totalPrice: Double? = null,
    @SerialName("id")
    val createdAt: String? = null,
    @SerialName("id")
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




