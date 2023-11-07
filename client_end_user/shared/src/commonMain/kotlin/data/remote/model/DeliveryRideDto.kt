package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class DeliveryRideDto(
    val id: String,
    val restaurantName: String,
    val restaurantImage: String,
    val startPoint: LocationDto,
    val destination: LocationDto,
    val startPointAddress: String,
    val destinationAddress: String,
    val price: Double,
    val tripStatus: Int,
)