package org.thechance.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
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
import kotlin.math.ceil
import kotlin.math.floor


@Composable
fun RatingBar(
    rating: Double,
    count: Int,
    selectedIcon: Painter,
    notSelectedIcon: Painter,
    halfSelectedIcon: Painter,
    iconsSize: Dp = 24.dp,
    modifier: Modifier = Modifier,
) {
    var ratings by remember { mutableStateOf(rating) }
     ratings = rating
    if (ratings > count) ratings = 5.0
    Row(modifier = modifier) {
        repeat(
            when {
                (ratings.rem(1) <= 0.9) -> {
                    floor(ratings).toInt()
                }

                else -> ceil(ratings).toInt()
            }
        ) {
            Image(
                painter = selectedIcon,
                contentDescription = null,
                modifier = Modifier.size(iconsSize)
            )
        }
        if (ratings.rem(1) in 0.5..0.9) {
            println(ratings)
            Image(
                painter = halfSelectedIcon,
                contentDescription = null,
                modifier = Modifier.size(iconsSize)
            )
        }
        repeat(
            when {
                (ratings.rem(1) >= 0.5) -> {
                    (count - ceil(ratings)).toInt()
                }

                else -> (count - floor(ratings)).toInt()
            }
        ) {
            Image(
                painter = notSelectedIcon,
                contentDescription = null,
                modifier = Modifier.size(iconsSize)
            )
        }
    }
}