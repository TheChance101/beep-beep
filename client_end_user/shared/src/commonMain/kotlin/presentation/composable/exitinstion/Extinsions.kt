package presentation.composable.exitinstion

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
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

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color, isVisible: Boolean = true) = composed(
    factory = {
        if (isVisible) {
            val density = LocalDensity.current
            val strokeWidthPx = density.run { strokeWidth.toPx() }

            Modifier.drawBehind {
                val width = size.width
                val height = size.height - strokeWidthPx / 2

                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = height),
                    end = Offset(x = width, y = height),
                    strokeWidth = strokeWidthPx
                )
            }
        } else this
    }
)

fun Modifier.topBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width

            drawLine(
                color = color,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = width, y = 0f),
                strokeWidth = strokeWidthPx
            )
        }
    }
)