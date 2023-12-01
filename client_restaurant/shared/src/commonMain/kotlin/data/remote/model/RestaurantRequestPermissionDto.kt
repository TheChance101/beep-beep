package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantRequestPermissionDto(
    @SerialName("id") val id: String? = null,
    @SerialName("restaurantName") val restaurantName: String? = null,
    @SerialName("ownerEmail") val ownerEmail: String? = null,
    @SerialName("cause") val cause: String? = null
)