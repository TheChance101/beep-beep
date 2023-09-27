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
import org.thechance.common.presentation.util.kms

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PermissionsFlowRow(
    allPermissions: List<PermissionUiState>,
    selectedPermissions: List<PermissionUiState>,
    onUserPermissionClicked: (PermissionUiState) -> Unit,
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colors.background)
            .padding(8.kms)
    ) {
        allPermissions.forEach { permission ->
            println("Permission: $permission")
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
fun getPermissionInfo(permission: PermissionUiState): PermissionInfo {
    return when (permission) {
        PermissionUiState.RESTAURANT_OWNER -> PermissionInfo(
            Resources.Drawable.restaurantOutlined,
            Resources.Strings.restaurantPermission
        )

        PermissionUiState.DRIVER -> PermissionInfo(
            Resources.Drawable.taxiOutlined,
            Resources.Strings.taxiPermission
        )

        PermissionUiState.END_USER -> PermissionInfo(
            Resources.Drawable.endUser,
            Resources.Strings.endUserPermission
        )

        PermissionUiState.SUPPORT -> PermissionInfo(
            Resources.Drawable.support,
            Resources.Strings.supportPermission
        )

        PermissionUiState.DELIVERY -> PermissionInfo(
            Resources.Drawable.delivery,
            Resources.Strings.deliveryPermission
        )

        PermissionUiState.ADMIN -> PermissionInfo(
            Resources.Drawable.dashboardAdmin,
            Resources.Strings.adminPermission
        )
    }
}

data class PermissionInfo(
    val iconPath: String,
    val title: String,
)