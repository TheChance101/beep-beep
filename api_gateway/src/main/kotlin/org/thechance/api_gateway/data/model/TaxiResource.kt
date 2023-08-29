package org.thechance.api_gateway.data.model

import kotlinx.serialization.Serializable


@Serializable
data class TaxiResource(
    val id: String,
    val plateNumber: String,
    val color: Int,
    val type: String,
    val driverId: String,
    val isAvailable: Boolean = true,
    val seats: Int = 4,
)
