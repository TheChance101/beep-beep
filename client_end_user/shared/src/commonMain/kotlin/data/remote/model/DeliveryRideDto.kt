package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class DeliveryRideDto(
    val id: String,
    val startPoint: LocationDto,
    val destination: LocationDto,
    val status: Int,
)