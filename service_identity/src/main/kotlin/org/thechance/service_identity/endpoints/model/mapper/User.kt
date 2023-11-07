package org.thechance.service_identity.endpoints.model.mapper

import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.entity.UserInfo
import org.thechance.service_identity.endpoints.model.UserDto
import org.thechance.service_identity.endpoints.model.UserRegistrationDto

fun User.toDto() = UserDto(
    id = id,
    fullName = fullName,
    username = username,
    email = email,
    phone = phone,
    walletBalance = walletBalance,
    currency = currency,
    country = country,
    addresses = addresses.toDto(),
    permission = permission
)

fun UserRegistrationDto.toEntity() = UserInfo(
    id = "",
    fullName = fullName,
    username = username,
    email = email,
    phone = phone,
    addresses = listOf(Address(id = "", address = address)),
)