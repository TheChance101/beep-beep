package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.Serializable

@Serializable
data class LocationResource(
    val latitude: Double,
    val longitude: Double
)
