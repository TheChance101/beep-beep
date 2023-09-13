package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.thechance.service_identity.domain.util.Role

@Serializable
data class UserDto(
    @Contextual
    val id: String? = null,
    val fullName: String? = null,
    val username: String? = null,
    val email: String? = null,
    val country: String? = null,
    val walletBalance: Double? = 0.0,
    val addresses: List<AddressDto> = emptyList(),
    val permission: Int = Role.END_USER
)