package org.thechance.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.floor


@Composable
fun RatingBar(
    rating: Double,
    count: Int,
    selectedIcon: Painter,
    halfSelectedIcon: Painter,
    notSelectedIcon: Painter,
    iconsSize: Dp = 24.dp,
    iconsPadding: Dp = 0.dp,
    modifier: Modifier = Modifier,
) {
    var ratings by remember { mutableStateOf(rating) }
    ratings = rating
    if (ratings > count) throw Exception("rating is bigger than count")
    Row(modifier = modifier) {
        repeat(count) { position ->
            Image(
                painter = when {
                    position < floor(ratings) || (position == floor(ratings).toInt() && ratings.rem(
                        1
                    ) >= 0.9) -> selectedIcon

                    position < ratings && ratings.rem(1) in 0.5..0.9 -> halfSelectedIcon
                    else -> notSelectedIcon
                },
                contentDescription = null,
                modifier = Modifier.padding(iconsPadding).size(iconsSize)
            )
        }
    }
}