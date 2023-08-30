package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.AddressCollection
import org.thechance.service_identity.domain.entity.Address

fun AddressCollection.toEntity(): Address {
    return Address(
        id = id.toString(),
        location = location.toEntity(),
    )
}

fun List<AddressCollection>.toEntity(): List<Address> {
    return this.map { it.toEntity() }
}
