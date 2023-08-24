package org.thechance.common.presentation.uistate

import org.thechance.common.domain.entity.User
import org.thechance.common.presentation.composables.table.Header


data class UserScreenUiState(
    val users: List<UserUiState> = emptyList(),
    val tableHeader: List<Header> = listOf(
        Header("No.", 1f),
        Header("Users", 3f),
        Header("Username", 3f),
        Header("Email", 3f),
        Header("Country", 3f),
        Header("Permission", 3f),
        Header("", 1f),
    ),
    val numberOfUsers: Int = 0,
    val search: String = "",
    val permissionsDialog: PermissionsDialogUiState = PermissionsDialogUiState(),
    val selectedUser: String = "",
    val selectedPage: Int = 1,
    val numberItemInPage: Int = 3,
    val pageCount: Int = 3,
    val filter: FilterUiState = FilterUiState(),
    val allPermissions: List<PermissionUiState> = listOf(
        PermissionUiState.RESTAURANT_OWNER,
        PermissionUiState.TAXI_DRIVER,
        PermissionUiState.END_USER,
        PermissionUiState.SUPPORT,
        PermissionUiState.DELIVERY,
        PermissionUiState.DASHBOARD_ADMIN,
    ),
    val userMenu: MenuUiState = MenuUiState(),
) {
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

    data class FilterUiState(
        val show: Boolean = false,
        val permissions: List<PermissionUiState> = emptyList(),
        val countries: List<CountryUiState> = listOf(
            CountryUiState(
                name = "Iraq",
                selected = true,
            ),
            CountryUiState(
                name = "Palestine",
                selected = true,
            ),
            CountryUiState(
                name = "Jordan",
                selected = true,
            ),
            CountryUiState(
                name = "Syria",
                selected = true,
            ),
            CountryUiState(
                name = "Egypt",
                selected = true,
            ),
        )
    )

    data class CountryUiState(
        val name: String = "",
        val selected: Boolean = false
    )

    data class MenuUiState(
        val username: String = "",
        val items: List<MenuItemUiState> = listOf(
            MenuItemUiState(
                iconPath = "ic_edit.xml",
                text = "Edit",
            ),
            MenuItemUiState(
                iconPath = "ic_delete.svg",
                text = "Delete",
                isSecondary = true,
            ),
        )
    ) {
        data class MenuItemUiState(
            val iconPath: String = "",
            val text: String = "",
            val isSecondary: Boolean = false,
        )
    }
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
            },
        )
    }
}