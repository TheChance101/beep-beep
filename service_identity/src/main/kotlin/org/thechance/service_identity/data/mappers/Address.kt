package org.thechance.service_identity.data.mappers

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