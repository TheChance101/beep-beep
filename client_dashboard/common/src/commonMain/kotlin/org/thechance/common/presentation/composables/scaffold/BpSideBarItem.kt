package org.thechance.common.presentation.composables.scaffold

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.composables.modifier.cursorHoverIconHand
import org.thechance.common.presentation.util.kms

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.BpSideBarItem(
    onClick: () -> Unit,
    isSelected: Boolean,
    selectedIconResource: String,
    unSelectedIconResource: String,
    mainMenuIsExpanded: Boolean,
    sideBarUnexpandedWidthInKms: Dp,
    label: String,
    modifier: Modifier = Modifier,
    layoutDirection: LayoutDirection = LocalLayoutDirection.current
) {
    val iconSize: Dp by remember { mutableStateOf(24.kms) }

    // Calculate translation based on layout direction
    val translationsX = when (layoutDirection) {
        LayoutDirection.Ltr -> (sideBarUnexpandedWidthInKms.value - iconSize.value) / 2
        LayoutDirection.Rtl -> -(sideBarUnexpandedWidthInKms.value - iconSize.value) / 2
    }

    Row(
            horizontalArrangement = Arrangement.spacedBy(32.kms + iconSize),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
                .onClick(onClick = onClick)
                .cursorHoverIconHand()
    ) {
        Icon(
                painterResource(if (isSelected) selectedIconResource else unSelectedIconResource),
                contentDescription = null,
                tint = if (isSelected) Theme.colors.primary else Theme.colors.contentSecondary,
                modifier = Modifier
                    .size(iconSize)
                    .graphicsLayer { translationX=translationsX }
        )
        AnimatedVisibility(
                visible = mainMenuIsExpanded,
                enter = fadeIn(tween(500)),
                exit = fadeOut(),
        ) {
            Text(
                    label,
                    style = Theme.typography.headline,
                    color = if (isSelected) Theme.colors.primary else Theme.colors.contentSecondary,
                    maxLines = 1,
            )
        }
    }
}
