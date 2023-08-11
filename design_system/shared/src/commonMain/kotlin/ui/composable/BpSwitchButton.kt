package com.beepbeep.designSystem.ui.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme.dimens
import com.beepbeep.designSystem.ui.theme.Theme.shapes
import com.beepbeep.designSystem.ui.theme.Theme.color


@Composable
fun BpSwitchButton(
    onSwitch: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    val targetBackgroundColor by animateColorAsState(
        targetValue = if (selected) color.primary else color.background,
        animationSpec = tween(500)
    )

    val targetBorderColor by animateColorAsState(
        targetValue = if (selected) color.primary else color.tertiaryContainer,
        animationSpec = tween(500)
    )
    val horizontalBias by animateFloatAsState(
        targetValue = when (selected) {
            true -> 1f
            else -> -1f
        },
        animationSpec = tween(500)
    )
    val alignment = remember { derivedStateOf { BiasAlignment(
        horizontalBias = horizontalBias, verticalBias = 0f) } }

    Box(modifier = modifier
             .width(dimens.size48)
            .height(dimens.size24)
            .background(color = targetBackgroundColor, shape = shapes.large)
            .border(width = 1.dp, color = targetBorderColor, shape = shapes.large)
            .clickable(indication = null, interactionSource = remember { MutableInteractionSource() })
            { onSwitch(!selected) },
        contentAlignment = alignment.value
    ) {
        Circle(
            modifier = Modifier.padding(dimens.space4),
            isSelected = selected
        )
    }
}

@Composable
private fun Circle(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    Canvas(modifier = modifier.size(size = dimens.size18).background(color = Color.Transparent)){
        drawCircle(
            color = if (isSelected) Color.White else Color(0xFFF53D47),
            radius = 18f
        )
    }
}