package org.thechance.service_identity.entity

data class UserDetails(
    val id: String,
    val userId: String,
    val password: String,
    val email: String,
    val addresses: List<Address>,
    val permissions: List<Permission>
)
