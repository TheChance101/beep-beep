package org.thechance.service_identity.domain.entity

data class User(
    val id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val walletBalance: Double = 0.0,
    val addresses: List<Address> = emptyList(),
    val permissions: List<Permission> = emptyList()
)

data class UpdateUserRequest(
    val fullName: String? = null,
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
)

data class CreateUserRequest(
    val fullName: String,
    val username: String,
    val password: String,
    val email: String,
)
