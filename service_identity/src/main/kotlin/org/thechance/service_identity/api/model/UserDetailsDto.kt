package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsDto(
    val id: String,
    val userId: String,
    val password: String,
    val email: String,
    val wallet: WalletDto,
    val addresses: List<String>,
    val permissions: List<String>
)
