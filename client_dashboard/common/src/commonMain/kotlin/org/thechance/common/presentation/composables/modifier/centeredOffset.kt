package org.thechance.common.presentation.composables.modifier

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun Modifier.centerItem(
    targetState: Boolean,
    parentWidth: Dp,
    itemWidth: Dp? = null,
    expandedSpace: Dp = Theme.dimens.space24,
    tween: AnimationSpec<Dp> = tween(500)
): Modifier {
    val positionedItemWidth = remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current.density
    return this.then(
        onGloballyPositioned {
            positionedItemWidth.value = itemWidth ?: (it.size.toSize().width / density).dp
        }.offset(
            x = animateDpAsState(
                targetValue =
                if (!targetState) parentWidth / 2 - positionedItemWidth.value / 2
                else expandedSpace,
                animationSpec = tween
            ).value
        )
    )
}