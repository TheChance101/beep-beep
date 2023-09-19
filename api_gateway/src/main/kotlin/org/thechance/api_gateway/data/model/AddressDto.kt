package org.thechance.api_gateway.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    @SerialName("id") val id: String? = null,
    @SerialName("location") val location: LocationDto,
    @SerialName("address") val address: String
)