package com.beepbeep.designSystem.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun BpNavigationBar(
    modifier: Modifier = Modifier,
    navigationBarHeight: Dp = 64.dp,
    backgroundColor: Color = Theme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    topBorder: Dp = 1.dp,
    borderColor: Color = Theme.colors.divider,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        shadowElevation = 0.dp,
        modifier = modifier
    ) {
        Row(
            Modifier.fillMaxWidth().height(navigationBarHeight).selectableGroup().drawBehind {
                drawRect(
                    color = borderColor,
                    topLeft = Offset(0f, 0f),
                    size = size.copy(height = topBorder.toPx()),
                )
            },
            horizontalArrangement = horizontalArrangement,
            content = content
        )
    }
}

@Composable
fun RowScope.BpNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable (tint: Color) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable ((style: TextStyle) -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val styledIcon = @Composable {
        val iconColor by animateColorAsState(
            if (selected) Theme.colors.primary else Theme.colors.contentTertiary,
        )
        icon(iconColor)
    }

    val styledLabel = @Composable {
        val textColor by animateColorAsState(
            if (selected) Theme.colors.primary else Theme.colors.contentTertiary,
        )
        val style =
            Theme.typography.caption.copy(color = textColor)
        label?.let {
            it(style)
        }
    }

    Box(
        modifier.selectable(
            indication = null,
            interactionSource = interactionSource,
            selected = selected,
            onClick = onClick,
            enabled = enabled,
            role = Role.Tab,
        ).selectableGroup().fillMaxHeight().weight(1f)
    ) {
        Column(
            modifier = modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            styledIcon()
            AnimatedVisibility((selected && label != null)) {
                styledLabel()
            }
        }
    }
}
