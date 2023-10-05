package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DeliveryRequestPermissionDto(
    @SerialName("restaurantName") val restaurantName: String,
    @SerialName("ownerEmail") val ownerEmail: String,
    @SerialName("cause") val cause: String
)