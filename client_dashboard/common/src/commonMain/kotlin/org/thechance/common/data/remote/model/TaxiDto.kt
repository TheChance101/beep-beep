package org.thechance.common.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaxiDto(
    @SerialName("id")
    val id: String?,
    @SerialName("plate_number")
    val plateNumber: String?,
    @SerialName("color")
    val color: Int?,
    @SerialName("type")
    val type: String?,
    @SerialName("seats")
    val seats: Int?,
    @SerialName("username")
    val username: String?,
    @SerialName("status")
    val status: Int?,
    @SerialName("trips")
    val trips: String?,
)