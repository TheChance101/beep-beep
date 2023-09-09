package org.thechance.common.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaxiInfoDto(
    // TODO : Change DriverId to Driver username after backend update
    @SerialName("driverId") val driverId: String,
    @SerialName("plateNumber") val plateNumber: String,
    @SerialName("color") val color: String,
    @SerialName("type") val type: String,
    @SerialName("seats") val seats: Int,
)