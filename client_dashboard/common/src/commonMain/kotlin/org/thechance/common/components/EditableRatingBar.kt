package org.thechance.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.floor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditableRatingBar(
    rating: Double,
    count: Int,
    selectedIcon: Painter,
    halfSelectedIcon: Painter,
    notSelectedIcon: Painter,
    onClick: (Double) -> Unit,
    modifier: Modifier = Modifier,
    iconsSize: Dp = 24.dp,
    iconsPadding: Dp = 0.dp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
) {
    val editableRating = remember { mutableStateOf(rating) }
    val mousePosition = remember { mutableStateOf(0.0) }

    if (editableRating.value > count) throw Exception("rating is bigger than count")

    Row(modifier = modifier
        .onPointerEvent(PointerEventType.Exit) { editableRating.value = rating }
        .onPointerEvent(PointerEventType.Press) { onClick(editableRating.value) },
        horizontalArrangement = horizontalArrangement
    ) {
        repeat(count) { iconPosition ->
            Image(
                painter = when {
                    iconPosition < floor(editableRating.value) ||
                            (iconPosition == floor(editableRating.value).toInt()
                                    && editableRating.value.rem(1) >= 0.9) -> selectedIcon

                    iconPosition < editableRating.value && editableRating.value.rem(1) in 0.5..0.9 -> halfSelectedIcon
                    else -> notSelectedIcon
                },
                contentDescription = null,
                modifier = Modifier.padding(iconsPadding).size(iconsSize)
                    .onPointerEvent(PointerEventType.Move) {
                        mousePosition.value = currentEvent.changes[0].position.x.toDouble()
                    }
                    .onPointerEvent(PointerEventType.Move) {
                        editableRating.value = when {

                            (mousePosition.value / size.width) >= 0.9 -> iconPosition + 1.0

                            (mousePosition.value / size.width) in (0.5..0.9) -> iconPosition + 0.5

                            else -> iconPosition.toDouble()
                        }
                    }
            )
        }
    }
}