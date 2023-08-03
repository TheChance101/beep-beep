package org.thechance.service_identity.domain.entity

data class UserDetails(
    val userId: String,
    val email: String?,
    val walletId: String?,
    val addresses: List<String>,
    val permissions: List<String>
)