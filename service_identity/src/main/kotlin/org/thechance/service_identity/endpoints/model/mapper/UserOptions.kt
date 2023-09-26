package org.thechance.service_identity.endpoints.model.mapper

import org.thechance.service_identity.domain.entity.UserOptions
import org.thechance.service_identity.endpoints.model.UserOptionsDto

fun UserOptionsDto.toEntity() = UserOptions(
    page = page ?: 10,
    limit = limit ?: 10,
    query = query,
    permissions = permissions,
    country = country
)