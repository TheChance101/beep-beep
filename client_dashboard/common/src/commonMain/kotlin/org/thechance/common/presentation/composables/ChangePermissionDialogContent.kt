package org.thechance.common.presentation.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.beepbeep.designSystem.ui.composable.BpChip
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTextButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.uistate.UserScreenUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChangePermissionDialogContent() {
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
                onClick = { },
                painter = painterResource(permission.iconPath),
                isSelected = false,
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
            onClick = { },
            modifier = Modifier.weight(1f)
        )
        BpOutlinedButton(
            title = "Save",
            onClick = { },
            modifier = Modifier.weight(3f),
//                height = 32.dp // todo: need to customize height
        )
    }
}

@Composable
@Preview
fun ChangePermissionDialogContentPreview() {
    ChangePermissionDialogContent()
}