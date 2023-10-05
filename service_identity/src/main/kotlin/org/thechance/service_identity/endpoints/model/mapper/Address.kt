package org.thechance.service_identity.endpoints.model.mapper

import org.thechance.service_identity.data.collection.mappers.toEntity
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.endpoints.model.AddressDto

fun Address.toDto(): AddressDto {
    return AddressDto(
        id = id,
        location = location.toDto(),
        address = address
    )
}

fun List<Address>.toDto(): List<AddressDto> {
    return map { it.toDto() }
}

fun AddressDto.toEntity() = Address(
    id = id ?: "",
    location = location.toEntity(),
    address = address
)

fun List<AddressDto>.toEntity() = map { it.toEntity() }

