package org.thechance.api_gateway.data.model.taxi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaxiDto(
    @SerialName("id") val id: String? = null,
    @SerialName("plateNumber") val plateNumber: String,
    @SerialName("color") val color: Long,
    @SerialName("type") val type: String,
    @SerialName("driverId") val driverId: String? = null,
    @SerialName("driverUsername") val driverUsername: String,
    @SerialName("isAvailable") val isAvailable: Boolean = true,
    @SerialName("seats") val seats: Int = 4,
    @SerialName("tripsCount") val tripsCount: Int? = 0,
)
