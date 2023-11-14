package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripDto(
    @SerialName("id")
    val id: String,
    @SerialName("clientName")
    val clientName: String,
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
    val tripStatus: Int
)
