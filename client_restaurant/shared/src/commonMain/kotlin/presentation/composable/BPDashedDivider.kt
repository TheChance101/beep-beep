package presentation.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
@Composable
fun BPDashedDivider(
    modifier: Modifier = Modifier,
    showDiamondIcon: Boolean = false,
    color: Color = Theme.colors.divider,
    strokeWidth: Float = 1f,
    dashWidth: Dp = 10.dp,
    spaceWidth: Dp = 5.dp,
) {
    val diamondOffset = if (showDiamondIcon) { 40f } else { 0f }
    Row(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxWidth()) {
            val pattern =
                floatArrayOf(dashWidth.value, spaceWidth.value, dashWidth.value, spaceWidth.value)

            if (showDiamondIcon) {
                drawPath(
                    color = color,
                    path = Path().apply {
                        moveTo(10f, 0f)
                        lineTo(18f, -8f)
                        lineTo(26f, 0f)
                        lineTo(18f, 8f)
                        close()
                    }
                )
            }

            drawLine(
                color = color,
                start = Offset(diamondOffset, 0f),
                end = Offset(size.width - diamondOffset, 0f),
                strokeWidth = strokeWidth,
                pathEffect = PathEffect.dashPathEffect(pattern, 0f)
            )

            if (showDiamondIcon) {
                drawPath(
                    color = color,
                    path = Path().apply {
                        moveTo(size.width - 10f, 0f)
                        lineTo(size.width - 18f, -8f)
                        lineTo(size.width - 26f, 0f)
                        lineTo(size.width - 18f, 8f)
                        close()
                    }
                )
            }
        }
    }
}

