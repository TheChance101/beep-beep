package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.thechance.service_identity.domain.util.Role

@Serializable
data class UserDto(
    @SerialName("id") val id: String? = null,
    @SerialName("fullName") val fullName: String? = null,
    @SerialName("username") val username: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("walletBalance") val walletBalance: Double? = 0.0,
    @SerialName("currency") val currency: String? = null,
    @SerialName("addresses") val addresses: List<AddressDto> = emptyList(),
    @SerialName("country") val country: String? = null,
    @SerialName("permission") val permission: Int = Role.END_USER,
)
