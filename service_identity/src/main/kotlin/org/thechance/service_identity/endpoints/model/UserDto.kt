package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String? = null,
    val fullName: String? = null,
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
    val walletBalance: Double? = 0.0,
    val addresses: List<AddressDto> = emptyList(),
    val permissions: List<PermissionDto> = emptyList()
)