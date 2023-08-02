package org.thechance.service_taxi.api.models.trip

import kotlinx.serialization.Serializable

@Serializable
data class TripDto(
    val id: String? = null,
    val driverId: String? = null,
    val clientId: String? = null,
    val from: String? = null,
    val to: String? = null,
    val rate: Double? = null,
    val price: Double? = null,
)
