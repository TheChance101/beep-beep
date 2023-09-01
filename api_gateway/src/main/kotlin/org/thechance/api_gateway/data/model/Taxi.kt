package org.thechance.api_gateway.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Taxi(
    val id: String? = null,
    val plateNumber: String,
    val color: Long,
    val type: String,
    val driverId: String,
    val isAvailable: Boolean = true,
    val seats: Int = 4,
)
