package org.thechance.common.presentation.composables.modifier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.toSize

@Composable
fun Modifier.centerItem(
    sideBarUnexpandedWidthInDp:Dp,
    itemWidth: Float? = null
): Modifier {
    val positionedItemWidth = remember { mutableStateOf(0f) }
    return this.then(
        onGloballyPositioned {
            positionedItemWidth.value = itemWidth ?: it.size.toSize().width
        }.graphicsLayer {
            translationX = (sideBarUnexpandedWidthInDp.toPx() - positionedItemWidth.value)/2
        }
    )
}