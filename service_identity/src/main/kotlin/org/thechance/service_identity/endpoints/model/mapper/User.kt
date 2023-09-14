package org.thechance.service_identity.endpoints.model.mapper

import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.endpoints.model.UserDto

fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        fullName = fullName,
        username = username,
        email = email,
        walletBalance = walletBalance,
        country = country,
        addresses = addresses.toDto(),
        permission = permission
    )
}
