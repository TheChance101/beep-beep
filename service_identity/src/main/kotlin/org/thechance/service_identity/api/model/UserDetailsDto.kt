package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.domain.entity.UserDetails

@Serializable
data class UserDetailsDto(
    val id: String,
    val userId: String,
    val password: String? = null,
    val email: String? = null,
    val walletId: String? = null,
    val addresses: List<String>,
    val permissions: List<String>
)

fun UserDetailsDto.toEntity(): UserDetails {
    return UserDetails(
        id = id,
        userId = userId,
        password = password,
        email = email,
        walletId = walletId,
        addresses = addresses,
        permissions = permissions
    )
}


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