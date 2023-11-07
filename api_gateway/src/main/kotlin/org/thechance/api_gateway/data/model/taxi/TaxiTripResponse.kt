package org.thechance.api_gateway.data.model.taxi

import kotlinx.serialization.Serializable
import org.thechance.api_gateway.data.model.LocationDto

@Serializable
data class TaxiTripResponse(
    val id: String,
    val clientName: String,
    val startPoint: LocationDto,
    val destination: LocationDto,
    val startPointAddress: String,
    val destinationAddress: String,
    val price: Double,
    val tripStatus: Int
)