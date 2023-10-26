package org.thechance.service_taxi.api.dto.taxi

import kotlinx.serialization.Serializable

@Serializable
data class TaxiDto(
    val id: String? = null,
    val plateNumber: String? = null,
    val color: Long? = null,
    val type: String? = null,
    val driverId: String? = null,
    val driverUsername: String? = null,
    val driverImage: String? = null,
    val rate: Double? = null,
    val isAvailable: Boolean? = null,
    val seats: Int? = null,
    val tripsCount: Int? = null,
)