package org.thechance.service_taxi.api.dto.taxi

import kotlinx.serialization.Serializable

@Serializable
data class TaxiDto(
    val id: String? = null,
    val plateNumber: String? = null,
    val color: Long? = null,
    val type: String? = null,
    val driverId: String? = null,
    val isAvailable: Boolean? = null,
    val seats: Int? = null,
)