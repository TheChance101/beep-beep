package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripDto(
    @SerialName("id")
    val id: String,
    @SerialName("restaurantName")
    val restaurantName: String,
    @SerialName("restaurantImage")
    val restaurantImage: String,
    @SerialName("startPoint")
    val startPointAddress: LocationDto? = null,
    @SerialName("destination")
    val destinationAddress: LocationDto? = null,
    @SerialName("tripStatus")
    val tripStatus: Int
)