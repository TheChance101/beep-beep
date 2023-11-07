package org.thechance.service_identity.domain.entity

data class Address(
    val id: String,
    val location: Location? = null,
    val address: String
)