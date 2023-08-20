package org.thechance.common.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpChip
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTextButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.uistate.UserScreenUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PermissionsDialogContent(
    dialogUiState: UserScreenUiState.PermissionsDialogUiState,
    togglePermission: (UserScreenUiState.PermissionUiState) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    Text(
        "Permission",
        style = Theme.typography.headline.copy(color = Theme.colors.contentPrimary),
        modifier = Modifier.padding(Theme.dimens.space24)
    )

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colors.background)
            .padding(Theme.dimens.space8)
    ) {
        UserScreenUiState.PermissionUiState.values().forEach { permission ->
            BpChip(
                label = permission.name.lowercase().capitalizeWords(),
                modifier = Modifier.padding(Theme.dimens.space8),
                onClick = { togglePermission(permission) },
                painter = painterResource(permission.iconPath),
                isSelected = dialogUiState.permissions.contains(permission)
            )
        }
    }

    Row(
        Modifier.fillMaxWidth().padding(Theme.dimens.space24),
        horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BpTextButton(
            text = "Cancel",
            onClick = onCancelClick,
            modifier = Modifier.weight(1f).height(32.dp)
        )
        BpOutlinedButton(
            title = "Save",
            onClick = onSaveClick,
            modifier = Modifier.weight(3f).height(32.dp),
        )
    }
}

private fun String.capitalizeWords(): String = split(" ")
    .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
    .replace("_", " ")