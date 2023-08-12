package org.thechance.service_taxi.domain.entity

import kotlinx.datetime.LocalDateTime

data class Trip(
    val id: String,
    val taxiId: String? = null,
    val driverId: String? = null,
    val clientId: String,
    val startPoint: Location,
    val destination: Location?,
    val rate: Double? = null,
    val price: Double,
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
)

data class Location(
    val latitude: Double,
    val longitude: Double,
)
