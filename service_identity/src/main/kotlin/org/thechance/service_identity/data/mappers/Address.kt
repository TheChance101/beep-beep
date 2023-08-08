package org.thechance.service_identity.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_identity.data.collection.AddressCollection
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.endpoints.model.AddressDto

fun AddressCollection.toEntity(): Address {
    return Address(
        id = id.toHexString(),
        userId = userId.toHexString(),
       location=location.toEntity(),
    )
}

fun AddressDto.toEntity(): Address {
    return Address(
        id = id,
        userId = userId,
       location = location.toEntity()
    )
}

fun Address.toDto(): AddressDto {
    return AddressDto(
        id = id,
        userId = userId,
        location = location.toDto()
    )
}

fun List<Address>.toDto(): List<AddressDto> {
    return map { it.toDto() }
}

fun Address.toCollection(): AddressCollection {
    return AddressCollection(
        userId = ObjectId(this.userId),
        location = this.location.toCollection()
    )
}

fun List<AddressCollection>.toEntity(): List<Address> {
    return this.map { it.toEntity() }
}