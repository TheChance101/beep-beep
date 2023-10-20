package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("currency") val currency: String? = null,
    @SerialName("restaurantId") val restaurantId: String? = null,
    @SerialName("restaurantName") val restaurantName: String? = null,
    @SerialName("cuisines") val cuisines: List<CuisineDto>? = null,
    @SerialName("image") val image: String? = null
)
