package org.thechance.service_identity.endpoints.model.mapper

import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.endpoints.model.AddressDto

fun Address.toDto(): AddressDto {
    return AddressDto(
        id = id,
        location = location.toDto()
    )
}

fun List<Address>.toDto(): List<AddressDto> {
    return map { it.toDto() }
}