package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//TODO Add image to offers
@Serializable
data class OfferDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("restaurants") val restaurants: List<RestaurantDto>? = null,
    @SerialName("image") val image: String? = null,
)
