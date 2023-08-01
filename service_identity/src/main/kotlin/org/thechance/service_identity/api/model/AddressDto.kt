package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.domain.entity.Address

@Serializable
data class AddressDto(
    val id: String,
    val userId: String,
    val country: String,
    val city: String,
    val street: String? = null,
    val zipCode: Long? = null,
    val houseNumber: String? = null
) {
    fun toAddress(): Address {
        return Address(
            id = id,
            userId = userId,
            country = country,
            city = city,
            street = street,
            zipCode = zipCode,
            houseNumber = houseNumber
        )
    }
}