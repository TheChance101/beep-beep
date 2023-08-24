package com.beepbeep.designSystem.ui.composable

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun <E : Enum<*>> BpAnimatedTabLayout(
    tabItems: List<E>,
    selectedTab: E,
    onTabSelected: (E) -> Unit,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = Theme.dimens.space4,
    selectedTabColor: Color = Theme.colors.primary,
    containerColor: Color = Theme.colors.surface,
    shape: Shape = RoundedCornerShape(Theme.radius.medium),
    borderStroke: BorderStroke? = BorderStroke(1.dp, Theme.colors.divider),
    content: @Composable (E) -> Unit
) {
    var selectedTabOffset by remember { mutableStateOf(Offset.Zero) }
    var tabSize by remember { mutableStateOf(IntSize.Zero) }
    val radiusPx = with(LocalDensity.current) { Theme.radius.small.toPx() }
    val selectedTabOffsetAnimated by animateOffsetAsState(selectedTabOffset)

    Row(
        modifier.fillMaxWidth().clip(shape).background(containerColor)
            .then(
                Modifier.takeIf { borderStroke != null }?.border(borderStroke!!, shape) ?: Modifier
            ).drawBehind {
                drawRoundRect(
                    selectedTabColor,
                    topLeft = selectedTabOffsetAnimated,
                    size = tabSize.toSize(),
                    cornerRadius = CornerRadius(radiusPx)
                )
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        tabItems.forEachIndexed { index, tab ->
            AnimatedTab(
                tab = tab,
                onTabSelected = onTabSelected,
                modifier = Modifier.padding(
                    start = if (index == 0) horizontalPadding else 0.dp,
                    end = if (index == tabItems.size - 1) horizontalPadding else 0.dp
                ).onGloballyPositioned { coordinates ->
                    if (tab == selectedTab) {
                        selectedTabOffset = coordinates.positionInParent()
                    }
                }.onSizeChanged { size -> if (tab == selectedTab) { tabSize = size } },
                content = content
            )
        }
    }
}

@Composable
private fun <E : Enum<*>> RowScope.AnimatedTab(
    tab: E,
    onTabSelected: (E) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Theme.radius.small),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable (E) -> Unit
) {
    Box(
        modifier.weight(1f).clip(shape).clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onTabSelected(tab) }
            ),
        contentAlignment = Alignment.Center
    ) {
        content(tab)
    }
}