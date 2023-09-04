package presentation.composable.exitinstion

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun Modifier.drawTopIndicator(
    xOffset: Float,
    indicatorWidth: Dp = 40.dp,
    indicatorHeight: Dp = 2.dp,
    indicatorColor: Color = Theme.colors.primary
): Modifier = then(
    Modifier.drawWithContent {
        drawContent()
        drawRoundRect(
            color = indicatorColor,
            topLeft = Offset(xOffset, 0f),
            size = size.copy(width = indicatorWidth.toPx(), height = indicatorHeight.toPx()),
            cornerRadius = CornerRadius(indicatorHeight.toPx() / 2, indicatorHeight.toPx() / 2)
        )
    }
)

@Composable
fun Dp.toPx(): Float = with(LocalDensity.current) { toPx() }