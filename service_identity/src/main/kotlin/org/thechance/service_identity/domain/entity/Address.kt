package org.thechance.service_identity.domain.entity

data class Address(
    val id: String,
    val userId: String,
    val latitude: Double,
    val longitude: Double,
    val isDeleted: Boolean = false
)