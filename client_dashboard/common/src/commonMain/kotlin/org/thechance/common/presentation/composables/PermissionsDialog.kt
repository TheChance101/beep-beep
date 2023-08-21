package org.thechance.common.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.uistate.UserScreenUiState

@Composable
fun PermissionsDialog(
    allPermissions: List<UserScreenUiState.PermissionUiState>,
    selectedPermissions: List<UserScreenUiState.PermissionUiState>,
    togglePermission: (UserScreenUiState.PermissionUiState) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background)
    ) {
        Text(
            "Permissions",
            style = Theme.typography.headline.copy(color = Theme.colors.contentPrimary),
            modifier = Modifier.padding(Theme.dimens.space24)
        )

        PermissionsFlowRow(
            allPermissions = allPermissions,
            selectedPermissions = selectedPermissions,
            onPermissionClicked = togglePermission
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Theme.dimens.space24),
            horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BpTransparentButton(
                title = "Cancel",
                onClick = onCancelClick,
                modifier = Modifier.weight(1f).height(36.dp)
            )
            BpOutlinedButton(
                title = "Save",
                onClick = onSaveClick,
                modifier = Modifier.weight(3f).height(36.dp),
            )
        }
    }
}