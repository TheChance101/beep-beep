package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantDto(
    @SerialName("id") val id: String,
    @SerialName("ownerId") val ownerId: String? = null,
    @SerialName("ownerUsername") val ownerUsername: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("image") val image: String? = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_1280.jpg",//null,
    @SerialName("description") val description: String? = null,
    @SerialName("priceLevel") val priceLevel: String? = null,
    @SerialName("rate") val rate: Double? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("openingTime") val openingTime: String? = null,
    @SerialName("closingTime") val closingTime: String? = null,
    @SerialName("location") val location: LocationDto,
    @SerialName("address") val address: String? = null
)
