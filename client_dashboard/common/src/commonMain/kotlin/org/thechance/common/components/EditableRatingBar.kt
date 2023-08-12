package org.thechance.common.components

import androidx.compose.foundation.layout.Row
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditableRatingBar(
    rating: Double,
    count: Int,
    selectedIcon: Painter,
    halfSelectedIcon: Painter,
    notSelectedIcon: Painter,
    iconsSize: Dp = 24.dp,
    onClick: (Double) -> Unit,
    modifier: Modifier = Modifier,
) {
    val editableRating = remember { mutableStateOf(rating) }
    val position = remember { mutableStateOf(0.0) }
    Row(
        modifier
            .onPointerEvent(PointerEventType.Exit) { editableRating.value = rating }
            .onPointerEvent(PointerEventType.Move) {
                position.value = currentEvent.changes[0].position.x.toDouble()
            }
            .onPointerEvent(PointerEventType.Move) {
                editableRating.value = roundRating(position.value / iconsSize.value.toInt())
            }
            .onPointerEvent(PointerEventType.Press) { onClick(editableRating.value) }
    ) {
        RatingBar(
            rating = editableRating.value,
            count = count,
            selectedIcon = selectedIcon,
            notSelectedIcon = notSelectedIcon,
            halfSelectedIcon = halfSelectedIcon,
            iconsSize = iconsSize,
        )
    }
}

private fun roundRating(number: Double): Double {
    val integerPart = number.toInt()
    val decimalPart = number - integerPart
    return if (decimalPart >= 0.6) integerPart + 1.0 else number
}