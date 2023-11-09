package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    @SerialName("id")
    val id: String,
    @SerialName("restaurantName")
    val restaurantName: String?=null,
    @SerialName("restaurantImage")
    val restaurantImage: String?=null,
    @SerialName("startPoint")
    val startPoint: LocationDto,
    @SerialName("destination")
    val destination: LocationDto,
    @SerialName("startPointAddress")
    val startPointAddress: String,
    @SerialName("destinationAddress")
    val destinationAddress: String,
    @SerialName("price")
    val price: Double,
    @SerialName("tripStatus")
    val tripStatus: Int,
)