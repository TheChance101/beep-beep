package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class TaxiTripDto(
    val id: String,
    val clientName: String,
    val startPoint: LocationDto,
    val destination: LocationDto,
    val startPointAddress: String,
    val destinationAddress: String,
    val price: Double,
    val tripStatus: Int
)
