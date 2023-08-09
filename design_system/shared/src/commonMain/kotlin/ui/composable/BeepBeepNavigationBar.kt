package com.beepbeep.designSystem.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme

@Composable
fun BeepBeepNavigationBar(
    modifier: Modifier = Modifier,
    navigationBarHeight: Dp = 64.dp,
    backgroundColor: Color = BeepBeepTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = BottomNavigationDefaults.Elevation,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        modifier = modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(navigationBarHeight)
                .selectableGroup(),
            horizontalArrangement = horizontalArrangement,
            content = content
        )
    }
}

@Composable
fun RowScope.BeepBeepNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable (tint: Color) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable ((style: TextStyle) -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val styledIcon = @Composable {
        val iconColor by animateColorAsState(
            if (selected) BeepBeepTheme.colorScheme.primary else BeepBeepTheme.colorScheme.onSecondary,
            tween(500)
        )
        icon(tint = iconColor)
    }

    val styledLabel = @Composable {
        val textColor by animateColorAsState(
            if (selected) BeepBeepTheme.colorScheme.primary else BeepBeepTheme.colorScheme.onSecondary,
            tween(500)
        )
        val style =
            BeepBeepTheme.typography.caption.copy(color = textColor)
        label?.let {
            it(style)
        }
    }

    Box(
        modifier
            .selectable(
                indication = null,
                interactionSource = interactionSource,
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
            ).selectableGroup()
            .fillMaxHeight()
            .weight(1f)
    ) {
        Column(
            modifier = modifier.align(Alignment.TopCenter)
        ) {
            AnimatedVisibility(selected) {
                Indicator(40.dp)
            }
        }
        Column(
            modifier = modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            styledIcon()
            AnimatedVisibility(alwaysShowLabel || (selected && label != null)) {
                Spacer(modifier = Modifier.width(4.dp))
                styledLabel()
            }
        }
    }
}

@Composable
private fun Indicator(
    width: Dp,
    height: Dp = 3.dp,
    shape: Shape = BeepBeepTheme.shapes.small,
    color: Color = BeepBeepTheme.colorScheme.primary
) {
    Box(modifier = Modifier.width(width).height(height).clip(shape).background(color))
}