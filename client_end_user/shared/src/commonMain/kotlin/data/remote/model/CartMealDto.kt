package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartMealDto(
    @SerialName("mealId") val mealId: String? = null,
    @SerialName("currency") val currency: String? = null,
    @SerialName("restaurantName") val restaurantName: String? = null, // TODO: add in api gateway
    @SerialName("name") val name: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("quantity") val quantity: Int? = null,
    @SerialName("price") val price: Double? = null
)