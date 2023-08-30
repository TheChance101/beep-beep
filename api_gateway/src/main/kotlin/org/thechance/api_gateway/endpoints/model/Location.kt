package org.thechance.api_gateway.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val latitude: Double? = null,
    val longitude: Double? = null
)
