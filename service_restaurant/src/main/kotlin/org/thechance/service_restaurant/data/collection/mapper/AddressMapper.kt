package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.AddressCollection
import org.thechance.service_restaurant.entity.Address

fun AddressCollection.toEntity(): Address {
    return Address(
        id = id.toString(),
        lat = lat,
        lon = lon,
        isDeleted = isDeleted,
    )
}

fun List<AddressCollection>.toEntity(): List<Address> = map { it.toEntity() }

fun Address.toCollection(): AddressCollection {
    return AddressCollection(
        lat = lat,
        lon = lon,
        isDeleted = isDeleted,
    )
}