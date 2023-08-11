package org.thechance.service_taxi.domain.entity

import kotlinx.datetime.LocalDateTime

data class TripUpdateRequest(
    val id: String,
    val taxiId: String? = null,
    val driverId: String? = null,
    val clientId: String? = null,
    val startPoint: Location? = null,
    val destination: Location? = null,
    val rate: Double? = null,
    val price: Double? = null,
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
)