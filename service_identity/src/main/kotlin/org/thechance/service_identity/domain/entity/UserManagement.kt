package org.thechance.service_identity.domain.entity

data class UserManagement(
    val id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val country: String,
    val permission: Int
)
