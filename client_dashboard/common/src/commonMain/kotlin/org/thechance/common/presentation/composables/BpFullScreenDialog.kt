package org.thechance.common.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpChip
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTextButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.LocalScreenSize
import org.thechance.common.presentation.uistate.UserScreenUiState

@Composable
fun BpFullScreenDialog(
    show: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 400.dp,
    shape: Shape = RoundedCornerShape(Theme.radius.medium),
    overlayColor: Color = Color(0x99000000).copy(alpha = 0.25f),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable ColumnScope.() -> Unit
) {
    val size = LocalScreenSize.current
    val screenSize = DpSize(size.width.dp, size.height.dp)
    AnimatedVisibility(show, enter = fadeIn(), exit = fadeOut()) {
        Box(
            modifier.requiredSize(screenSize).background(color = overlayColor)
                .clickable(interactionSource, null, onClick = onDismissRequest),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = (Theme.colors.surface),
                ),
                modifier = Modifier.width(cardWidth).clickable(enabled = false) {},
                shape = shape,
            ) {
                content()
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PreviewDialog(){
    var dialogState by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize().background(color = Theme.colors.background)) {
        Text("User Screen", color = Color.Red)

        Button(onClick = { dialogState = true }) {
            Text("show dialog")
        }

    }

    BpFullScreenDialog(
        show = dialogState,
        onDismissRequest = { dialogState = false },
    ) {
        Text(
            "Permission",
            style = Theme.typography.headline.copy(color = Theme.colors.contentPrimary),
            modifier = Modifier.padding(Theme.dimens.space24)
        )
        FlowRow(
            Modifier.fillMaxWidth().background(Theme.colors.background),
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
                onClick = { dialogState = false },
                modifier = Modifier.weight(1f)
            )
            BpOutlinedButton(
                title = "Save",
                onClick = { dialogState = false },
                modifier = Modifier.weight(3f),
//                height = 32.dp // todo: need to customize height
            )
        }
    }
}


fun String.capitalizeWords(): String = split("_")
    .joinToString(" ") { s ->
        s.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }