package org.thechance.common.data.remote.model

import org.thechance.common.domain.entity.User

data class UserDto(
    val id: String,
    val fullName: String,
    val username: String,
    val country: String,
    val email: String,
    val permissions: List<PermissionDto> = emptyList(),
    val imageUrl: String,
) {
    data class PermissionDto(
        val id: Int,
        val permission: String
    )
}

fun UserDto.toEntity() = User(
    id = id,
    fullName = fullName,
    country = country,
    username = username,
    email = email,
    permission = permissions.map { enumValueOf(it.permission) },
    imageUrl = imageUrl
)

fun List<UserDto>.toEntity() = this.map { user ->
    User(
        id = user.id,
        fullName = user.fullName,
        country = user.country,
        username = user.username,
        email = user.email,
        permission = user.permissions.map { enumValueOf(it.permission) },
        imageUrl = user.imageUrl
    )
}