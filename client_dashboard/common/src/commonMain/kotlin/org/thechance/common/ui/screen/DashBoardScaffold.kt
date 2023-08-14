package org.thechance.common.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun DashBoardScaffold(
    appbar:@Composable ()-> Unit,
    sideBar:@Composable ()-> Unit,
    content: @Composable (Dp) -> Unit,
) {
    Row(Modifier.fillMaxSize()) {
        sideBar()
        Column {
            appbar()
            content(40.dp)
        }
    }
}



private fun DrawScope.drawBottomBorder(
    strokeWidth: Dp,
    color: Color,
    shareStart: Boolean,
    shareEnd: Boolean
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
        color = color
    )
}


