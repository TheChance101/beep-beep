package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class TaxiRideDto(
    val id: String,
    val taxiPlateNumber: String,
    val taxiDriverName: String,
    val driverImage: String? = null,
    val carType: String? = null,
    val taxiColor: Long,
    val startPoint: LocationDto,
    val destination: LocationDto,
    val startPointAddress: String,
    val destinationAddress: String,
    val rate: Double? = null,
    val tripStatus: Int,
)