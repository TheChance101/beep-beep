package org.thechance.service_identity.entity

import org.thechance.service_identity.api.model.AddressDto

data class Address(
    val id: String,
    val userId: String,
    val country: String,
    val city: String,
    val street: String? = null,
    val zipCode: Long? = null,
    val houseNumber: String? = null
){
    fun toAddressDto(): AddressDto {
        return AddressDto(
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