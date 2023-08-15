package org.thechance.common.ui.composables.modifier

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp

@Stable
fun Modifier.border(
    end: BorderStroke? = null,
    bottom: BorderStroke? = null,
) =
    drawBehind {
        end?.let {
            drawEndBorder(end.width, end.brush)
        }
        bottom?.let {
            drawBottomBorder(bottom.width, bottom.brush)
        }
    }

private fun DrawScope.drawEndBorder(
    strokeWidth: Dp,
    color: Brush,
    shareTop: Boolean = true,
    shareBottom: Boolean = true
) {
    val strokeWidthPx = strokeWidth.toPx()
    if (strokeWidthPx == 0f) return
    drawPath(
        Path().apply {
            val width = size.width
            val height = size.height
            moveTo(width, 0f)
            lineTo(width - strokeWidthPx, if (shareTop) strokeWidthPx else 0f)
            lineTo(width - strokeWidthPx, if (shareBottom) height - strokeWidthPx else height)
            lineTo(width, height)
            close()
        },
        brush = color
    )
}

private fun DrawScope.drawBottomBorder(
    strokeWidth: Dp,
    color: Brush,
    shareStart: Boolean = true,
    shareEnd: Boolean = true
) {
    val strokeWidthPx = strokeWidth.toPx()
    if (strokeWidthPx == 0f) return

    drawPath(
        Path().apply {
            val width = size.width
            val height = size.height
            moveTo(0f, height)
            lineTo(if (shareStart) strokeWidthPx else 0f, height - strokeWidthPx)
            lineTo(if (shareEnd) width - strokeWidthPx else width, height - strokeWidthPx)
            lineTo(width, height)
            close()
        },
        brush = color
    )
}