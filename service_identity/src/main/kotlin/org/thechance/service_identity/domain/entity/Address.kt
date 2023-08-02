package org.thechance.service_identity.domain.entity

import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import org.thechance.service_identity.api.model.AddressDto
import org.thechance.service_identity.data.collection.AddressCollection

data class Address(
    val id: String,
    val userId: String,
    val country: String,
    val city: String,
    val street: String? = null,
    val zipCode: Long? = null,
    val houseNumber: String? = null,
    val isDeleted: Boolean? = null
) {
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

    fun toAddressCollection(): AddressCollection {
        return AddressCollection(
            userId = ObjectId(this.userId).toId(),
            country = this.country,
            city = this.city,
            street = this.street,
            zipCode = this.zipCode,
            houseNumber = this.houseNumber
        )
    }
}