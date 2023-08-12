package org.thechance.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
    Row(modifier) {
        RatingBar(
            rating = editableRating.value,
            count = count,
            selectedIcon = selectedIcon,
            notSelectedIcon = notSelectedIcon,
            halfSelectedIcon = halfSelectedIcon,
            iconsSize = iconsSize,
            modifier = Modifier
                .onPointerEvent(PointerEventType.Exit) { editableRating.value = rating }
                .onPointerEvent(PointerEventType.Move) {
                    position.value = currentEvent.changes[0].position.x.toDouble()
                }
                .onPointerEvent(PointerEventType.Move) {
                    editableRating.value = position.value / iconsSize.value.toInt()
                }
                .onPointerEvent(PointerEventType.Press) { onClick(editableRating.value) }
                .padding(end = 8.dp)
        )
    }
}