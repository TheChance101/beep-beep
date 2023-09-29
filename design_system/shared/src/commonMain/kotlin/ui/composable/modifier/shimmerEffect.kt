package com.beepbeep.designSystem.ui.composable.modifier


import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun Modifier.shimmerEffect(
    colors: List<Color> = listOf(
        Theme.colors.disable,
        Theme.colors.disable.copy(alpha = 0.5f),
        Theme.colors.disable,
    ),
    durationMillis: Int = 1000,
): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffset by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis),
        ),
    )

    background(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset(startOffset, 0f),
            end = Offset(startOffset + size.width, size.height.toFloat()),
        )
    ).onGloballyPositioned { size = it.size }
}