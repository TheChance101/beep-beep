package org.thechance.service_taxi.domain.entity

data class Taxi(
    val id: String,
    val plateNumber: String, // index
    val color: String,
    val type: String,
    val driverId: String,
    val isDeleted: Boolean = false,
    val isAvailable: Boolean = true,
    val capacity: Int, // 1->normal etc..
)