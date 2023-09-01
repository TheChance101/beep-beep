package org.thechance.common.presentation.overview

import org.thechance.common.domain.entity.Permission
import org.thechance.common.domain.entity.User
import org.thechance.common.presentation.util.ErrorState


data class OverviewUiState(
    val isLoading: Boolean = false,
    val error: ErrorState = ErrorState.UnKnownError,
    val users: List<LatestRegisteredUserUiState> = emptyList(),
    val dropdownMenuState: DropdownMenuState = DropdownMenuState(),
)

data class LatestRegisteredUserUiState(
    val name: String,
    val image: String,
    val permission: PermissionUiState,
)

data class DropdownMenuState(
    val isExpanded: Boolean = false,
    val items: List<String> = listOf("Daily", "Weekly", "Monthly"),
    val selectedIndex: Int = 0,
)

fun User.toUiState(): LatestRegisteredUserUiState {
    return LatestRegisteredUserUiState(
        name = fullName,
        image = imageUrl,
        permission = permission.first().toUiState(),
    )
}

enum class PermissionUiState {
    RESTAURANT,
    DRIVER,
    END_USER,
    SUPPORT,
    DELIVERY,
    ADMIN,
}

fun Permission.toUiState(): PermissionUiState {
    return when (this) {
        Permission.RESTAURANT -> PermissionUiState.RESTAURANT
        Permission.DRIVER -> PermissionUiState.DRIVER
        Permission.END_USER -> PermissionUiState.END_USER
        Permission.SUPPORT -> PermissionUiState.SUPPORT
        Permission.DELIVERY -> PermissionUiState.DELIVERY
        Permission.ADMIN -> PermissionUiState.ADMIN
    }
}




