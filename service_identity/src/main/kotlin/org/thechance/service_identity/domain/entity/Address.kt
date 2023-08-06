package org.thechance.service_identity.domain.entity

data class Address(
    val id: String,
    val userId: String,
    val location: Location,
    val isDeleted: Boolean = false
)