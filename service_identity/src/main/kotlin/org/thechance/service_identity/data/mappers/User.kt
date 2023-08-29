package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.entity.UserManagement
import org.thechance.service_identity.endpoints.model.UserDto
import java.util.*

fun User.toDto(): UserDto {
    return UserDto(
        id = UUID.fromString(id).toString(),
        fullName = fullName,
        username = username,
        email = email,
        walletBalance = walletBalance,
        addresses = addresses.toDto(),
        permission = permission
    )
}
