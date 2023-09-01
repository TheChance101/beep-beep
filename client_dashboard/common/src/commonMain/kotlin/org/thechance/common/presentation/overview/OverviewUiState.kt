package org.thechance.common.presentation.overview

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

fun User.Permission.toUiState(): PermissionUiState {
    return when (this) {
        User.Permission.RESTAURANT -> PermissionUiState.RESTAURANT
        User.Permission.DRIVER -> PermissionUiState.DRIVER
        User.Permission.END_USER -> PermissionUiState.END_USER
        User.Permission.SUPPORT -> PermissionUiState.SUPPORT
        User.Permission.DELIVERY -> PermissionUiState.DELIVERY
        User.Permission.ADMIN -> PermissionUiState.ADMIN
    }
}




