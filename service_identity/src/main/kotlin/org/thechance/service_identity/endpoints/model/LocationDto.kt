package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val latitude: Double,
    val longitude: Double,
)

@Serializable
data class CreateLocationBody(
    val latitude: Double,
    val longitude: Double,
)

@Serializable
data class UpdateLocationBody(
    val latitude: Double? = null,
    val longitude: Double? = null,
)