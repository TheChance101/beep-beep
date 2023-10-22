package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//Start and endDate Should be Long from backend
@Serializable
data class TripDto(
    @SerialName("id") val id: String? = null,
    @SerialName("taxiId") val taxiId: String? = null,
    @SerialName("driverId") val driverId: String? = null,
    @SerialName("clientId") val clientId: String? = null,
    @SerialName("orderId") val orderId: String? = null,
    @SerialName("restaurantId") val restaurantId: String? = null,
    @SerialName("taxiPlateNumber") val taxiPlateNumber: String? = null,
    @SerialName("taxiDriverName") val taxiDriverName: String? = null,
    @SerialName("taxiColor") val taxiColor: Long? = null,
    @SerialName("startPoint") val startPoint: LocationDto? = null,
    @SerialName("destination") val destination: LocationDto? = null,
    @SerialName("startPointAddress") val startPointAddress: String? = null,
    @SerialName("destinationAddress") val destinationAddress: String? = null,
    @SerialName("rate") val rate: Double? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("startDate") val startDate: String? = null,
    @SerialName("endDate") val endDate: String? = null,
    @SerialName("isATaxiTrip") val isATaxiTrip: Boolean? = null,
    @SerialName("tripStatus") val tripStatus: Int = 0,
)