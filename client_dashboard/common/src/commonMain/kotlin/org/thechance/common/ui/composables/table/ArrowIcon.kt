package org.thechance.common.ui.composables.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArrowIcon(
    painter: Painter,
    contentDescription: String? = null,
    onClick: () -> Unit,
    enable: Boolean = true
) {
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier.size(24.dp).onClick {
            if (enable) {
                onClick()
            }
        },
        tint = if (!enable) {
            Color.Gray
        } else {
            Color.Black
        }
    )
}