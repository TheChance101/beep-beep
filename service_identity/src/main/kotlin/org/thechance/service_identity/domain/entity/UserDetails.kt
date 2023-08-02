package org.thechance.service_identity.domain.entity

import org.thechance.service_identity.api.model.UserDetailsDto

data class UserDetails(
    val id: String,
    val userId: String,
    val password: String?,
    val email: String?,
    val walletId: String?,
    val addresses: List<String>,
    val permissions: List<String>
)

fun UserDetails.toDto(): UserDetailsDto {
    return UserDetailsDto(
        id = id,
        userId = userId,
        password = password,
        email = email,
        walletId = walletId,
        addresses = addresses,
        permissions = permissions
    )
}
