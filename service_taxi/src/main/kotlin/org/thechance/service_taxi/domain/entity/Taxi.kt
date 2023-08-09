package org.thechance.service_taxi.domain.entity

data class Taxi(
    val id: String,
    val plateNumber: String,
    val color: String,
    val type: String,
    val driverId: String,
    val isAvailable: Boolean = true,
    val seats: Int = 4,
)