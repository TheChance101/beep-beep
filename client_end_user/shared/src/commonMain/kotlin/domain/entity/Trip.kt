package domain.entity

import data.remote.model.LocationDto
import kotlinx.serialization.Serializable

@Serializable
data class Trip(
    val id: String,
    val taxiId: String,
    val taxiPlateNumber: String,
    val driverId: String,
    val clientId: String,
    val startPoint: LocationDto,
    val destination: LocationDto,
    val rate: Double,
    val price: Double,
    val startDate: String,
    val endDate: String,
    val timeToArriveInMints: Int,
)