package org.thechance.common.data.gateway.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaxiDto(
    @SerialName("id")
    val id: String?,
    @SerialName("plateNumber")
    val plateNumber: String?,
    @SerialName("color")
    val color: Long?,
    @SerialName("type")
    val type: String?,
    @SerialName("driverId")
    val driverId: String? = null,
    @SerialName("seats")
    val seats: Int?,
    @SerialName("driverUsername")
    val driverUsername: String?,
    @SerialName("isAvailable")
    val isAvailable: Boolean?,
    @SerialName("trips")
    val trips: String?,
)