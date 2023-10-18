package org.thechance.api_gateway.data.model.taxi

import kotlinx.serialization.Serializable
import org.thechance.api_gateway.data.model.LocationDto

@Serializable
data class DeliveryTrackingResponse(
    val id: String,
    val startPoint: LocationDto,
    val destination: LocationDto,
    val status: Int
)
