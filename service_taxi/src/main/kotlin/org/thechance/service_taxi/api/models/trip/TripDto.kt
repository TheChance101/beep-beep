package org.thechance.service_taxi.api.models.trip

import kotlinx.serialization.Serializable

@Serializable
data class TripDto(
    val id: String? = null,
    val driverId: String? = null,
    val clientId: String? = null,
    val from: Pair<Long, Long>? = null,
    val to: Pair<Long, Long>? = null,
    val rate: Double? = null,
    val price: Double? = null,
)
