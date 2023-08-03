package org.thechance.service_identity.data.mappers

import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import org.thechance.service_identity.api.model.AddressDto
import org.thechance.service_identity.data.collection.AddressCollection
import org.thechance.service_identity.domain.entity.Address

fun AddressCollection.toEntity(): Address {
    return Address(
        id = id.toHexString(),
        userId = userId.toString(),
        country = country,
        city = city,
        street = street,
        zipCode = zipCode,
        houseNumber = houseNumber,
        isDeleted = isDeleted
    )
}
fun AddressDto.toEntity(): Address {
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

fun Address.toDto(): AddressDto {
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

fun Address.toCollection(): AddressCollection {
    return AddressCollection(
        userId = ObjectId(this.userId).toId(),
        country = this.country,
        city = this.city,
        street = this.street,
        zipCode = this.zipCode,
        houseNumber = this.houseNumber
    )
}