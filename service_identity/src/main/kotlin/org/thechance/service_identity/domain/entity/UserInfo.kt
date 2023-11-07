package org.thechance.service_identity.domain.entity

data class UserInfo (
    val id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val phone: String,
    val addresses: List<Address> = emptyList(),
)