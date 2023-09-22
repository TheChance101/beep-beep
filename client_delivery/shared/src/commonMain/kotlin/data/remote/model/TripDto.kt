package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("taxiId")
    val taxiId: String? = null,
    @SerialName("driverId")
    val driverId: String? = null,
    @SerialName("clientId")
    val clientId: String? = null,
    @SerialName("startPoint")
    val startPoint: LocationDto? = null,
    @SerialName("destination")
    val destination: LocationDto? = null,
    @SerialName("rate")
    val rate: Double? = null,
    @SerialName("price")
    val price: Double? = null,
    @SerialName("startDate")
    val startDate: String? = null,
    @SerialName("endDate")
    val endDate: String? = null,
)