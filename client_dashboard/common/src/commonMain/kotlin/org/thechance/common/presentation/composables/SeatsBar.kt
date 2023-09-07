package org.thechance.common.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.thechance.common.presentation.util.kms
import kotlin.math.floor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SeatsBar(
    count: Int,
    selectedIcon: Painter,
    notSelectedIcon: Painter,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    selectedSeatsCount: Int = 0,
    iconsSize: Dp = 24.kms,
    iconsPadding: PaddingValues = PaddingValues(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
) {

    var currentCount by remember { mutableStateOf(selectedSeatsCount) }
    var isEditingMode by remember { mutableStateOf(false) }

    Row(modifier = modifier
        .onPointerEvent(PointerEventType.Exit) {
            currentCount = selectedSeatsCount
            isEditingMode = false
        }
        .onPointerEvent(PointerEventType.Press) { onClick(currentCount) },
        horizontalArrangement = horizontalArrangement
    ) {
        repeat(count) { iconPosition ->
            Image(
                painter = when {
                    iconPosition < floor(
                        if (isEditingMode) currentCount.toDouble() else selectedSeatsCount.toDouble()
                    ) -> selectedIcon

                    else -> notSelectedIcon
                },
                contentDescription = null,
                modifier = Modifier.padding(iconsPadding).size(iconsSize)
                    .onPointerEvent(PointerEventType.Move) {
                        isEditingMode = true
                        currentCount = iconPosition + 1
                    }
            )
        }
    }
}