package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.AddressCollection
import org.thechance.service_restaurant.domain.entity.Address

fun AddressCollection.toEntity(): Address {
    return Address(
        id = id.toString(),
        latitude = latitude,
        longitude = longitude,
    )
}

fun List<AddressCollection>.toEntity(): List<Address> = map { it.toEntity() }

fun Address.toCollection(): AddressCollection {
    return AddressCollection(
        latitude = latitude,
        longitude = longitude,
    )
}