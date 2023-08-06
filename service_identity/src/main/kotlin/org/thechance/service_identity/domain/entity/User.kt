package org.thechance.service_identity.domain.entity

data class User(
    val id: String,
    val fullName: String,
    val username: String,
    val password: String,
    val email: String,
    val wallet: Wallet,
    val addresses: List<String> = emptyList(),
    val permissions: List<Int> = emptyList()
)
