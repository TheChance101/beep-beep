package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.UserDto
import org.thechance.common.domain.entity.Permission
import org.thechance.common.domain.entity.User


fun UserDto.toEntity() = User(
    id = id ?: "",
    fullName = fullName ?: "",
    country = country ?: "",
    username = username ?: "",
    email = email ?: "",
    permission = mapIntToPermissions(permission),
    imageUrl = imageUrl ?: ""
)

fun List<UserDto>.toEntity() = map(UserDto::toEntity)

fun mapIntToPermissions(permissionNumbers: Int): List<Permission> {
    val permissionMapping = mapOf(
        1 to Permission.END_USER,
        2 to Permission.ADMIN,
        4 to Permission.RESTAURANT_OWNER,
        8 to Permission.DRIVER,
        16 to Permission.SUPPORT,
        32 to Permission.DELIVERY
    )
    val permissions = mutableListOf<Permission>()
    for ((key, value) in permissionMapping) {
        if (permissionNumbers and key == key) {
            permissions.add(value)
        }
    }
    return permissions
}