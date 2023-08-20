package org.thechance.common.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.LocalScreenSize

@Composable
fun BpFullScreenDialog(
    show: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 400.dp,
    shape: Shape = RoundedCornerShape(Theme.radius.medium),
    overlayColor: Color = Color(0x99000000).copy(alpha = 0.25f),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable ColumnScope.() -> Unit
) {
    val size = LocalScreenSize.current
    val screenSize = DpSize(size.width.dp, size.height.dp)
    AnimatedVisibility(show, enter = fadeIn(), exit = fadeOut()) {
        Box(
            modifier.requiredSize(screenSize).background(color = overlayColor)
                .clickable(interactionSource, null, onClick = onDismissRequest),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = (Theme.colors.surface),
                ),
                modifier = Modifier.width(cardWidth).clickable(enabled = false) {},
                shape = shape,
            ) {
                content()
            }
        }
    }
}