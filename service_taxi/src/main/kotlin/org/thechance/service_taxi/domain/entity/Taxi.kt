package org.thechance.service_taxi.domain.entity

data class Taxi(
    val id: String,
    val plateNumber: String? = null, // index
    val color: String? = null,
    val type: String? = null,
    val driverId: String? = null,
    val isAvailable: Boolean? = null,
    val capacity: Int? = null, // 1->normal etc..
)