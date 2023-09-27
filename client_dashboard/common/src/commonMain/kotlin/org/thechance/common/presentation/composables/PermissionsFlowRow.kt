package org.thechance.common.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.beepbeep.designSystem.ui.composable.BpChip
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.overview.PermissionUiState
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.users.PermissionInfo
import org.thechance.common.presentation.users.UserScreenUiState
import org.thechance.common.presentation.util.kms

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PermissionsFlowRow(
    allPermissions: List<UserScreenUiState.PermissionUiState>,
    selectedPermissions: List<UserScreenUiState.PermissionUiState>,
    onUserPermissionClicked: (UserScreenUiState.PermissionUiState) -> Unit,
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colors.background)
            .padding(8.kms)
    ) {
        allPermissions.forEach { permission ->
            BpChip(
                label = getPermissionInfo(permission).title,
                modifier = Modifier.padding(8.kms),
                onClick = { onUserPermissionClicked(permission) },
                painter = painterResource(getPermissionInfo(permission).iconPath),
                isSelected = selectedPermissions.contains(permission)
            )
        }
    }
}

@Composable
private fun getPermissionInfo(permission: UserScreenUiState.PermissionUiState): PermissionInfo {
    return when (permission) {
        UserScreenUiState.PermissionUiState.RESTAURANT_OWNER -> PermissionInfo(
            Resources.Drawable.restaurantOutlined,
            Resources.Strings.restaurantPermission
        )

        UserScreenUiState.PermissionUiState.DRIVER -> PermissionInfo(
            Resources.Drawable.taxiOutlined,
            Resources.Strings.taxiPermission
        )

        UserScreenUiState.PermissionUiState.END_USER -> PermissionInfo(
            Resources.Drawable.endUser,
            Resources.Strings.endUserPermission
        )

        UserScreenUiState.PermissionUiState.SUPPORT -> PermissionInfo(
            Resources.Drawable.support,
            Resources.Strings.supportPermission
        )

        UserScreenUiState.PermissionUiState.DELIVERY -> PermissionInfo(
            Resources.Drawable.delivery,
            Resources.Strings.deliveryPermission
        )

        UserScreenUiState.PermissionUiState.ADMIN -> PermissionInfo(
            Resources.Drawable.dashboardAdmin,
            Resources.Strings.adminPermission
        )
    }
}

