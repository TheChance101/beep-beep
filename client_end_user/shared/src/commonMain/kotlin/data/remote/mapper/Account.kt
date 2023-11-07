package data.remote.mapper

import data.remote.model.UserRegistrationDto
import domain.entity.Account

fun Account.toUserRegistrationDto(): UserRegistrationDto {
    return UserRegistrationDto(
        fullName = fullName,
        username = username,
        password = password,
        email = email,
        phone = phone,
        address = address
    )
}