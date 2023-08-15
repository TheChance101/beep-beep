package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.AddressCollection
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.endpoints.model.AddressDto

fun AddressCollection.toEntity(): Address {
    return Address(
        id = id.toHexString(),
        location = location.toEntity(),
    )
}

fun Address.toDto(): AddressDto {
    return AddressDto(
        id = id,
        location = location.toDto()
    )
}

fun List<Address>.toDto(): List<AddressDto> {
    return map { it.toDto() }
}


fun List<AddressCollection>.toEntity(): List<Address> {
    return this.map { it.toEntity() }
}
