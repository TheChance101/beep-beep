package org.thechance.api_gateway.data.model.taxi

import kotlinx.serialization.Serializable
import org.thechance.api_gateway.data.model.LocationDto

@Serializable
data class DeliveryTripResponse(
    val id: String,
    val restaurantName: String,
    val restaurantImage: String,
    val startPoint: LocationDto,
    val destination: LocationDto,
    val startPointAddress: String,
    val destinationAddress: String,
    val price: Double,
    val tripStatus: Int
)