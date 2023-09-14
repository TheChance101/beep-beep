package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaxiDto(
    @SerialName("id") val id: String? = null,
    @SerialName("plateNumber") val plateNumber: String? = null,
    @SerialName("color") val color: Long? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("driverId") val driverId: String? = null,
    @SerialName("isAvailable") val isAvailable: Boolean? = null,
    @SerialName("seats") val seats: Int? = null,
    @SerialName("timeToArriveInMints") val timeToArriveInMints: Int? = null,
)