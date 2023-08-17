package org.thechance.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beepbeep.designSystem.ui.theme.Theme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditablePriceBar(
    count: Int,
    text: String,
    enabledIconsColor: Color,
    disabledIconsColor: Color,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    priceLevel: Int = 0,
    textSize: TextUnit = 16.sp,
    textPadding: Dp = 0.dp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
) {
    val editablePriceLevel = remember { mutableStateOf(priceLevel) }

    if (count < 1 || priceLevel > count || priceLevel < 0) throw Exception("price level is Invalid")
    Row(
        modifier = modifier
            .onPointerEvent(PointerEventType.Exit) { editablePriceLevel.value = priceLevel }
            .onPointerEvent(PointerEventType.Press) { onClick(editablePriceLevel.value) },
        horizontalArrangement = horizontalArrangement
    ) {
        repeat(count) { iconPosition ->
            Text(
                text = text.repeat(iconPosition + 1),
                color = if (iconPosition + 1 == editablePriceLevel.value) enabledIconsColor else disabledIconsColor,
                fontFamily = Theme.typography.titleMedium.fontFamily,
                fontWeight = Theme.typography.titleMedium.fontWeight,
                fontSize = textSize,
                modifier = Modifier.padding(textPadding)
                    .onPointerEvent(PointerEventType.Move) {
                        editablePriceLevel.value = iconPosition + 1
                    }
            )
        }
    }
}