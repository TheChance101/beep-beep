package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class TripDto(
    val id: String? = null,
    val taxiId: String? = null,
    val driverId: String? = null,
    val clientId: String? = null,
    val startPoint: LocationDto? = null,
    val destination: LocationDto? = null,
    val rate: Double? = null,
    val price: Double? = null,
    val startDate: String? = null,
    val endDate: String? = null,
)