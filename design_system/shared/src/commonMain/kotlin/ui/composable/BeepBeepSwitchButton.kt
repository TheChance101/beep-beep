package com.beepbeep.designSystem.ui.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.colorScheme
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.shapes
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.dimens


@Composable
fun BeepBeepSwitchButton(
    selected: Boolean,
    modifier: Modifier = Modifier,
    onUpdate: (Boolean) -> Unit,
) {
    val targetBackgroundColor by animateColorAsState(
        targetValue = if (selected) colorScheme.primary else colorScheme.background,
        animationSpec = tween(500)
    )

    val targetBorderColor by animateColorAsState(
        targetValue = if (selected) colorScheme.primary else colorScheme.tertiaryContainer,
        animationSpec = tween(500)
    )
    val horizontalBias by animateFloatAsState(
        targetValue =  when (selected) {
            true -> 1f
            else -> -1f
        },
        animationSpec = tween(500)
    )
    val alignment=remember { derivedStateOf { BiasAlignment(horizontalBias = horizontalBias, verticalBias = 0f) } }

    Box(modifier = modifier.width(50.dp)
        .background(color=  targetBackgroundColor, shape = shapes.large).
        border(width = 1.dp, color =targetBorderColor, shape = shapes.large)
        .clickable(indication = null, interactionSource = remember { MutableInteractionSource() })
        {onUpdate(!selected) },
        contentAlignment = alignment.value
        ) {
            Circle(
                modifier = Modifier.padding(dimens.space4),
                isSelected = selected
            )
        }
}

@Composable
fun Circle(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    Card(
        shape = CircleShape, modifier = modifier.size(20.dp), elevation = 0.dp
    ) {
        Box(modifier = Modifier.background(if(isSelected)  Color.White else  colorScheme.primary) , )
    }
}