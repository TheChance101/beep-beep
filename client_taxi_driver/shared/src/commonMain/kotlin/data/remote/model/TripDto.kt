package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class TripDto(
    val id: String? = null,
    val taxiId: String? = null,
    val driverId: String? = null,
    val clientId: String? = null,
    val orderId: String? = null,
    val restaurantId: String? = null,
    val taxiPlateNumber: String? = null,
    val taxiDriverName: String? = null,
    val taxiColor: Long? = null,
    val startPoint: LocationDto? = null,
    val destination: LocationDto? = null,
    val startPointAddress: String? = null,
    val destinationAddress: String? = null,
    val rate: Double? = null,
    val price: Double? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val isATaxiTrip: Boolean? = null,
    val tripStatus: Int = 0
)