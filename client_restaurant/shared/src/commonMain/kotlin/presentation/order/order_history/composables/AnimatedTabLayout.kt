package presentation.order.order_history.composables

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun <E : Enum<*>> AnimatedTabLayout(
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
    Row(
        modifier.fillMaxWidth().clip(shape).background(containerColor)
            .then(
                Modifier.takeIf { borderStroke != null }?.border(borderStroke!!, shape) ?: Modifier
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        tabItems.forEachIndexed { index, tab ->
            AnimatedTab(
                tab = tab,
                isSelected = tab == selectedTab,
                onTabSelected = onTabSelected,
                selectedTabColor = selectedTabColor,
                containerColor = containerColor,
                modifier = Modifier.padding(
                    start = if (index == 0) horizontalPadding else 0.dp,
                    end = if (index == tabItems.size - 1) horizontalPadding else 0.dp
                ),
                content = content
            )
        }
    }
}

@Composable
private fun <E : Enum<*>> RowScope.AnimatedTab(
    tab: E,
    isSelected: Boolean,
    onTabSelected: (E) -> Unit,
    selectedTabColor: Color,
    containerColor: Color,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Theme.radius.small),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable (E) -> Unit
) {
    val color by animateColorAsState(if (isSelected) selectedTabColor else containerColor)

    Box(
        modifier.weight(1f)
            .clip(shape).background(color, shape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onTabSelected(tab) }
            ),
        contentAlignment = Alignment.Center
    ) {
        content(tab)
    }
}