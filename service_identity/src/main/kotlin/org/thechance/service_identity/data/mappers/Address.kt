package org.thechance.service_identity.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_identity.api.model.AddressDto
import org.thechance.service_identity.data.collection.AddressCollection
import org.thechance.service_identity.domain.entity.Address

fun AddressCollection.toEntity(): Address {
    return Address(
        id = id.toHexString(),
        userId = userId.toHexString(),
        latitude = latitude,
        longitude = longitude,
        isDeleted = isDeleted
    )
}

fun AddressDto.toEntity(): Address {
    return Address(
        id = id,
        userId = userId,
        latitude = latitude,
        longitude = longitude
    )
}

fun Address.toDto(): AddressDto {
    return AddressDto(
        id = id,
        userId = userId,
        latitude = latitude,
        longitude = longitude
    )
}

fun Address.toCollection(): AddressCollection {
    return AddressCollection(
        userId = ObjectId(this.userId),
        latitude = this.latitude,
        longitude = this.longitude
    )
}

fun List<AddressCollection>.toEntity(): List<Address> {
    return this.map { it.toEntity() }
}