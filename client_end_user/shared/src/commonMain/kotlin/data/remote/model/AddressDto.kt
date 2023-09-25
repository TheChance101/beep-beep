package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    @SerialName("address")
    val address: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("location")
    val location: LocationDto?
)