package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    val id: String,
    val userId: String,
    val country: String,
    val city: String,
    val street: String? = null,
    val zipCode: Long? = null,
    val houseNumber: String? = null
)