package org.thechance.api_gateway.data.model.identity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.thechance.api_gateway.data.model.AddressDto

@Serializable
data class UserDetailsDto(
    @SerialName("id") val id: String,
    @SerialName("fullName") val fullName: String,
    @SerialName("username") val username: String,
    @SerialName("email") val email: String,
    @SerialName("phone") val phone: String,
    @SerialName("walletBalance") val walletBalance: Double,
    @SerialName("currency") val currency: String,
    @SerialName("addresses") val addresses: List<AddressDto> = emptyList(),
    @SerialName("country") val country: String,
    @SerialName("permission") val permission: Int = 1
)
