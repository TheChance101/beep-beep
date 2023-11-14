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
    @SerialName("restaurantName")
    val restaurantName: String,
    @SerialName("restaurantImage")
    val restaurantImage: String,
    @SerialName("meals")
    val meals: List<MealDto>,
    @SerialName("totalPrice")
    val totalPrice: Double? = null,
    @SerialName("createdAt")
    val createdAt: Long? = null,
    @SerialName("currency")
    val currency: String? = null,
    @SerialName("orderStatus")
    val orderStatus: Int?=null
) {
    @Serializable
    data class MealDto(
        @SerialName("mealId")
        val id: String,
        @SerialName("image")
        val mealImageUrl: String,
        @SerialName("name")
        val mealName: String,
        @SerialName("quantity")
        val quantity: Int,
        @SerialName("price")
        val price: Double,
    )
}




