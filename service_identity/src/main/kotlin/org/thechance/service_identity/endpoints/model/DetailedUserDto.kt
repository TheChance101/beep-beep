package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable

@Serializable
data class DetailedUserDto(
    val id: String? = null,
    val fullName: String? = null,
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
    val walletId: String? = null,
    val addresses: List<String> = emptyList(),
    val permissions: List<String> = emptyList()
)