package org.thechance.common.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.users.UserScreenUiState
import org.thechance.common.presentation.util.kms
import java.awt.Dimension

@Composable
fun PermissionsDialog(
    visible: Boolean,
    allPermissions: List<UserScreenUiState.PermissionUiState>,
    selectedPermissions: List<UserScreenUiState.PermissionUiState>,
    onUserPermissionClicked: (UserScreenUiState.PermissionUiState) -> Unit,
    onSaveUserPermissions: () -> Unit,
    onCancelUserPermissionsDialog: () -> Unit,
) {
    Dialog(
        transparent = true,
        focusable = true,
        undecorated = true,
        visible = visible,
        onCloseRequest = onCancelUserPermissionsDialog,
    ) {
        this.window.minimumSize = Dimension(400, 340)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background)
        ) {
            Text(
                text = Resources.Strings.permissions,
                style = Theme.typography.headline.copy(color = Theme.colors.contentPrimary),
                modifier = Modifier.padding(24.kms)
            )

            PermissionsFlowRow(
                allPermissions = allPermissions,
                selectedPermissions = selectedPermissions,
                onUserPermissionClicked = onUserPermissionClicked
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.kms),
                horizontalArrangement = Arrangement.spacedBy(8.kms),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BpTransparentButton(
                    title = Resources.Strings.cancel,
                    onClick = onCancelUserPermissionsDialog,
                    modifier = Modifier.weight(1f)
                )
                BpOutlinedButton(
                    title = Resources.Strings.save,
                    onClick = onSaveUserPermissions,
                    modifier = Modifier.weight(3f),
                )
            }
        }
    }
}