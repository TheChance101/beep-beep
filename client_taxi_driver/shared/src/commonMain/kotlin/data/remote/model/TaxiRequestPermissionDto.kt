package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TaxiRequestPermissionDto(
    @SerialName("restaurantName") val driverFullName: String,
    @SerialName("ownerEmail") val driverEmail: String,
    @SerialName("cause") val description: String
)