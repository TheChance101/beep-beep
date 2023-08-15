package org.thechance.common.ui.composables.modifier

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.centerItem(
    targetState: Boolean,
    parentWidth: Dp,
    itemWidth: Dp,
    expandedSpace:Dp = 24.dp,
    tween: AnimationSpec<Dp> = tween(500)
): Modifier {
    return this.then(
        offset(
            x = animateDpAsState(
                targetValue =
                if (!targetState) parentWidth / 2 - itemWidth / 2
                else expandedSpace,
                animationSpec = tween
            ).value
        )
    )
}