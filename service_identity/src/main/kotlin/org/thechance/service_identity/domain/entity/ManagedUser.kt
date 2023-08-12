package org.thechance.service_identity.domain.entity

data class ManagedUser(
    val id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val permissions: List<Permission> = emptyList()
)
