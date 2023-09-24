package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartMealDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("currency") val currency: String? = null,
    @SerialName("restaurantName") val restaurantName: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("count") val count: Long? = null
)