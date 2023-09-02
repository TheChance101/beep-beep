package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.UserDto
import org.thechance.common.domain.entity.User


fun UserDto.toEntity() = User(
    id = id ?: "",
    fullName = fullName ?: "",
    country = country ?: "",
    username = username ?: "",
    email = email ?: "",
    permission = permissions?.map { enumValueOf(it?.permission ?: "") } ?: emptyList(),
)

fun List<UserDto>.toEntity() = map(UserDto::toEntity)