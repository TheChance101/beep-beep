package org.thechance.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import kotlin.math.ceil
import kotlin.math.floor


@Composable
fun RatingBar(
    rating: Double,
    count: Int,
    selectedIcon: Painter,
    notSelectedIcon: Painter,
    halfSelectedIcon: Painter,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        repeat(floor(rating).toInt()) {
            Icon(painter = selectedIcon, contentDescription = null)
        }
        if (!(rating.rem(1).equals(0.0))) {
            Icon(painter = halfSelectedIcon, contentDescription = null)
        }
        repeat((count - ceil(rating)).toInt()) {
            Icon(painter = notSelectedIcon, contentDescription = null)
        }
    }
}