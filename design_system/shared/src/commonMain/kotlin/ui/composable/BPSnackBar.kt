package com.beepbeep.designSystem.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun BPSnackBar(
    icon: Painter,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    iconTint: Color = Theme.colors.primary,
    iconBackgroundColor: Color = Theme.colors.hover,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
    ) {
        Column(modifier = modifier.fillMaxWidth().padding(horizontal = Theme.dimens.space16)) {
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = Theme.dimens.space16)
                    .border(
                        width = 1.dp,
                        color = Theme.colors.hover,
                        shape = RoundedCornerShape(size = Theme.radius.medium)
                    )
                    .background(
                        color = Theme.colors.surface,
                        shape = RoundedCornerShape(size = Theme.radius.medium)
                    )
                    .padding(horizontal = Theme.dimens.space16, vertical = Theme.dimens.space8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8)
            ) {
                Icon(
                    modifier = Modifier.background(
                        color = iconBackgroundColor,
                        shape = RoundedCornerShape(Theme.radius.medium)
                    ).padding(Theme.dimens.space8),
                    painter = icon,
                    contentDescription = null,
                    tint = iconTint
                )
                content()
            }
        }
    }
}
