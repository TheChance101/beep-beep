package org.thechance.common.presentation.composables.modifier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.toSize

@Composable
fun Modifier.centerItem(
    sideBarUnexpandedWidthInDp: Dp,
    itemWidth: Float? = null,
    layoutDirection: LayoutDirection = LocalLayoutDirection.current
): Modifier {
    val positionedItemWidth = remember { mutableStateOf(0f) }
    val translationsX = remember { mutableStateOf(0f) }

    // Determine the appropriate translation based on layout direction
    val layoutAwareTranslation = when (layoutDirection) {
        LayoutDirection.Ltr -> (sideBarUnexpandedWidthInDp.value - positionedItemWidth.value) / 2
        LayoutDirection.Rtl -> -(sideBarUnexpandedWidthInDp.value - positionedItemWidth.value) / 2
    }

    // Calculate the width when itemWidth is provided
    if (itemWidth != null) {
        positionedItemWidth.value = itemWidth
        translationsX.value = layoutAwareTranslation
    }

    return this.then(
            onGloballyPositioned {
                positionedItemWidth.value = itemWidth ?: it.size.toSize().width
                translationsX.value = layoutAwareTranslation
            }.graphicsLayer {
                translationX =translationsX.value
            }
    )
}
