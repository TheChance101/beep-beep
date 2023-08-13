package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsDto(
    val id: String,
    val fullName: String,
    val username: String,
    val password: String,
    val email: String,
    val wallet: WalletDto,
    val addresses: List<AddressDto> = emptyList(),
    val permissions: List<PermissionDto> = emptyList()
)