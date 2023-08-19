package org.thechance.common.presentation.uistate

import org.thechance.common.domain.entity.User


data class UserScreenUiState(
    val users: List<UserUiState> = emptyList(),
) {
    data class UserUiState(
        val fullName: String = "",
        val username: String = "",
        val email: String = "",
        val country: String = "",
        val permissions: List<PermissionUiState> = emptyList()
    )

    enum class PermissionUiState(val iconPath: String) {
        RESTAURANT("outline_restaurants.xml"),
        DRIVER("ic_taxi.xml"),
        END_USER("end_user.xml"),
        SUPPORT("ic_support.xml"),
        DELIVERY("ic_delivery.xml"),
        ADMIN("chart.xml"),
    }
}

fun User.toUiState() = UserScreenUiState.UserUiState(
    fullName = fullName,
    username = username,
    email = email,
    country = country,
    permissions = permission.map {
        when (it) {
            User.Permission.RESTAURANT -> UserScreenUiState.PermissionUiState.RESTAURANT
            User.Permission.DRIVER -> UserScreenUiState.PermissionUiState.DRIVER
            User.Permission.END_USER -> UserScreenUiState.PermissionUiState.END_USER
            User.Permission.SUPPORT -> UserScreenUiState.PermissionUiState.SUPPORT
            User.Permission.DELIVERY -> UserScreenUiState.PermissionUiState.DELIVERY
            User.Permission.ADMIN -> UserScreenUiState.PermissionUiState.ADMIN
        }
    }
)

fun List<User>.toUiState(): List<UserScreenUiState.UserUiState> {
    return map {
        UserScreenUiState.UserUiState(
            fullName = it.fullName,
            username = it.username,
            email = it.email,
            country = it.country,
            permissions = it.permission.map { permission ->
                when (permission) {
                    User.Permission.RESTAURANT -> UserScreenUiState.PermissionUiState.RESTAURANT
                    User.Permission.DRIVER -> UserScreenUiState.PermissionUiState.DRIVER
                    User.Permission.END_USER -> UserScreenUiState.PermissionUiState.END_USER
                    User.Permission.SUPPORT -> UserScreenUiState.PermissionUiState.SUPPORT
                    User.Permission.DELIVERY -> UserScreenUiState.PermissionUiState.DELIVERY
                    User.Permission.ADMIN -> UserScreenUiState.PermissionUiState.ADMIN
                }
            })
    }
}
