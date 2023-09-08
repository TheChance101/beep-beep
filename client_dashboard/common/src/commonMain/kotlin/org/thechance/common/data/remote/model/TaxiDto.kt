package org.thechance.common.data.remote.model

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
    val driverId: String?,
    @SerialName("seats")
    val seats: Int?,
    @SerialName("username")
    val username: String?,
    @SerialName("isAvailable")
    val isAvailable: Boolean?,
    @SerialName("trips")
    val trips: String?,
)