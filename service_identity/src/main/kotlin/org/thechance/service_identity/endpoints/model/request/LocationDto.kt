package org.thechance.service_identity.endpoints.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val latitude: Double,
    val longitude: Double,
)

@Serializable
data class UpdateLocationBody(
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
)