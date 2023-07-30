package org.thechance.service_identity.entity

data class Address(
    val id: String,
    val userId: String,
    val country: String,
    val city: String,
    val street: String? = null,
    val code: String? = null,
    val houseNumber: Int? = null
)