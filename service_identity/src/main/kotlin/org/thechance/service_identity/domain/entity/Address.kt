package org.thechance.service_identity.domain.entity

data class Address(
    val id: String,
    val userId: String,
    val country: String,
    val city: String,
    val street: String? = null,
    val zipCode: Long? = null,
    val houseNumber: String? = null,
    val isDeleted: Boolean? = null
)