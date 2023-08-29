package org.thechance.common.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.thechance.common.presentation.util.kms

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditablePriceBar(
    count: Int,
    icon: Painter,
    enabledIconsColor: Color,
    disabledIconsColor: Color,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    priceLevel: Int = 1,
    iconsSize: Dp = 32.kms,
    iconsPadding: PaddingValues = PaddingValues(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
) {
    val editablePriceLevel = remember { mutableStateOf(priceLevel) }

    when {
        (count < 1) -> throw Exception("count is smaller than 1")

        (priceLevel > count) -> throw Exception("price level is bigger than count")

        (priceLevel < 1) -> throw Exception("price level is smaller than 1")
    }
    Row(
        modifier = modifier
            .onPointerEvent(PointerEventType.Exit) { editablePriceLevel.value = priceLevel }
            .onPointerEvent(PointerEventType.Press) { onClick(editablePriceLevel.value) },
        horizontalArrangement = horizontalArrangement
    ) {
        repeat(count) { iconPosition ->
            Row(
                modifier = Modifier.padding(iconsPadding),
                horizontalArrangement = Arrangement.spacedBy(-iconsSize / 2)
            ) {
                repeat(iconPosition + 1) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = if (iconPosition + 1 == editablePriceLevel.value) enabledIconsColor else disabledIconsColor,
                        modifier = Modifier
                            .padding(0.dp)
                            .size(iconsSize)
                            .onPointerEvent(PointerEventType.Move) {
                                editablePriceLevel.value = iconPosition + 1
                            }
                    )
                }
            }
        }
    }
}