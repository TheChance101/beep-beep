package org.thechance.api_gateway.data.model
import org.thechance.api_gateway.data.model.AddressDto

data class UserDetailsDto(
    val id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val walletBalance: Double,
    val currency: String,
    val addresses: List<AddressDto> = emptyList(),
    val country: String,
    val permission: Int = 1
)
