package org.thechance.service_taxi.domain.entity

data class TaxiUpdateRequest(
    val id: String,
    val plateNumber: String? = null,
    val color: String? = null,
    val type: String? = null,
    val driverId: String? = null,
    val isAvailable: Boolean? = null,
    val seats: Int? = null,
)