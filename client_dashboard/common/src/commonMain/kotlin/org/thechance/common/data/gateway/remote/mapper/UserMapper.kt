package org.thechance.common.data.gateway.remote.mapper

import org.thechance.common.data.gateway.remote.model.UserDto
import org.thechance.common.domain.entity.Permission
import org.thechance.common.domain.entity.User
import org.thechance.common.presentation.users.UserScreenUiState


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

fun mapPermissionsToInt(permissions: List<Permission>):List<Int> {
    val permissionMapping = mapOf(
            Permission.END_USER to 1,
            Permission.ADMIN to 2,
            Permission.RESTAURANT_OWNER to 4,
            Permission.DRIVER to 8,
            Permission.SUPPORT to 16,
            Permission.DELIVERY to 32
    )
    return permissionMapping.filterKeys { permissions.contains(it) }.values.toList()
}
