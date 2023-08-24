package org.thechance.common.presentation.uistate

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.User
import org.thechance.common.presentation.composables.table.Header


data class UserScreenUiState(
    val tableHeader: List<Header> = listOf(
        Header("No.", 1f),
        Header("Users", 3f),
        Header("Username", 3f),
        Header("Email", 3f),
        Header("Country", 3f),
        Header("Permission", 3f),
        Header("", 1f),
    ),
    val pageInfo: UserPageInfoUiState = UserPageInfoUiState(),
    val specifiedUsers: Int = 1,
    val currentPage: Int = 1,
    val search: String = "",
    val isFilterDropdownMenuExpanded: Boolean = false,
    val permissionsDialog: PermissionsDialogUiState = PermissionsDialogUiState(),
) {
    data class UserPageInfoUiState(
        val data: List<UserUiState> = emptyList(),
        val numberOfUsers: Int = 0,
        val totalPages: Int = 0,
    )

    data class UserUiState(
        val fullName: String = "",
        val username: String = "",
        val email: String = "",
        val country: String = "",
        val permissions: List<PermissionUiState> = emptyList(),
    )

    enum class PermissionUiState(val iconPath: String) {
        RESTAURANT_OWNER("outline_restaurants.xml"),
        TAXI_DRIVER("ic_taxi.xml"),
        END_USER("ic_end_user.xml"),
        SUPPORT("ic_support.xml"),
        DELIVERY("ic_delivery.xml"),
        DASHBOARD_ADMIN("ic_admin.xml"),
    }

    data class PermissionsDialogUiState(
        val show: Boolean = false,
        val username: String = "",
        val permissions: List<PermissionUiState> = emptyList(),
    )
}

fun List<User>.toUiState(): List<UserScreenUiState.UserUiState> {
    return map {
        UserScreenUiState.UserUiState(
            fullName = it.fullName,
            username = it.username,
            email = it.email,
            country = it.country,
            permissions = it.permission.map { permission ->
                when (permission) {
                    User.Permission.RESTAURANT -> UserScreenUiState.PermissionUiState.RESTAURANT_OWNER
                    User.Permission.DRIVER -> UserScreenUiState.PermissionUiState.TAXI_DRIVER
                    User.Permission.END_USER -> UserScreenUiState.PermissionUiState.END_USER
                    User.Permission.SUPPORT -> UserScreenUiState.PermissionUiState.SUPPORT
                    User.Permission.DELIVERY -> UserScreenUiState.PermissionUiState.DELIVERY
                    User.Permission.ADMIN -> UserScreenUiState.PermissionUiState.DASHBOARD_ADMIN
                }
            })
    }
}

fun DataWrapper<User>.toUiState(): UserScreenUiState.UserPageInfoUiState {
    return UserScreenUiState.UserPageInfoUiState(
        data = result.toUiState(),
        totalPages = totalPages,
        numberOfUsers = numberOfResult
    )
}