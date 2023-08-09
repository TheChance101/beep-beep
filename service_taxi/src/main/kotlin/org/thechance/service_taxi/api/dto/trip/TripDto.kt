package org.thechance.service_taxi.api.dto.trip

import kotlinx.serialization.Serializable
import org.thechance.service_taxi.data.collection.LocationCollection

@Serializable
data class TripDto(
    val id: String? = null,
    val taxiId: String? = null,
    val driverId: String? = null,
    val clientId: String? = null,
    val startPoint: LocationCollection? = null,
    val destination: LocationCollection? = null,
    val rate: Double? = null,
    val price: Double? = null,
    val startDate: String? = null,
    val endDate: String? = null,
)
