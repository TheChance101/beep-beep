package org.thechance.service_identity.domain.entity

data class User(
    val id: String,
    val fullName: String,
    val username: String,
    val password: String,
    val email: String,
    val walletBalance: Double = 0.0,
    val addresses: List<Address> = emptyList(),
    val permissions: List<Permission> = emptyList()
)
