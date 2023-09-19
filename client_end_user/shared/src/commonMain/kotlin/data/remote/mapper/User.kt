package data.remote.mapper

import data.remote.model.UserDto
import domain.entity.User

fun UserDto.toUser(): User {
    return User(
        name = fullName ?: "",
        walletValue = 0.0,
        currency = ""
    )
}