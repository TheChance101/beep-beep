package org.thechance.service_taxi.api.models.trip

import kotlinx.serialization.Serializable
import org.thechance.service_taxi.data.collection.LatLongCollection

@Serializable
data class TripDto(
    val id: String? = null,
    val taxiId: String? = null,
    val driverId: String? = null,
    val clientId: String? = null,
    val startPoint: LatLongCollection? = null,
    val destination: LatLongCollection? = null,
    val rate: Double? = null,
    val price: Double? = null,
    val startDate: String? = null,
    val endDate: String? = null,
)
