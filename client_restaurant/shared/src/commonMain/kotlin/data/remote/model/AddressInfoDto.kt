package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressInfoDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("location")
    val location: LocationDto? = null,
    @SerialName("address")
    val address: String? = null,
)