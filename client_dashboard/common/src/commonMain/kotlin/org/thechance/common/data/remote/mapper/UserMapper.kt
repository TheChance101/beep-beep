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
    permission = mapIntToPermissions(permissions),
    imageUrl = imageUrl
)

fun List<UserDto>.toEntity() = map(UserDto::toEntity)

fun mapIntToPermissions(permissions: Int): List<Permission> {
    val permissionList = mutableListOf<Permission>()

    if (permissions and Role.END_USER != 0) {
        permissionList.add(Permission.END_USER)
    }
    if (permissions and Role.DASHBOARD_ADMIN != 0) {
        permissionList.add(Permission.ADMIN)
    }
    if (permissions and Role.RESTAURANT_OWNER != 0) {
        permissionList.add(Permission.RESTAURANT)
    }
    if (permissions and Role.TAXI_DRIVER != 0) {
        permissionList.add(Permission.DRIVER)
    }
    if (permissions and Role.SUPPORT != 0) {
        permissionList.add(Permission.SUPPORT)
    }
    if (permissions and Role.DELIVERY != 0) {
        permissionList.add(Permission.DELIVERY)
    }

    return permissionList
}

object Role {
    const val END_USER = 1
    const val DASHBOARD_ADMIN = 2
    const val RESTAURANT_OWNER = 4
    const val TAXI_DRIVER = 8
    const val SUPPORT = 16
    const val DELIVERY = 32
}