package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealCartDto (
    @SerialName("restaurantId") val restaurantId: String,
    @SerialName("mealId") val mealId: String,
    @SerialName("quantity") val quantity: Int,
)
