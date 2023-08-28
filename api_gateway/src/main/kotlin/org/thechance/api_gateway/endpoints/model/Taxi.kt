package org.thechance.api_gateway.endpoints.model

data class Taxi(
    val id: String,
    val plateNumber: String,
    val color: Int,
    val type: String,
    val driverId: String,
    val isAvailable: Boolean = true,
    val seats: Int = 4,
)
