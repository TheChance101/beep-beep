package org.thechance.service_identity.domain.entity

data class User(
    val id: String? = null,
    val fullName: String?,
    val username: String?,
    val password: String?,
    val isDeleted: Boolean = false,
    val email: String? = null,
    val walletId: String? = null,
    val addresses: List<String> = emptyList(),
    val permissions: List<Int> = emptyList()
)
