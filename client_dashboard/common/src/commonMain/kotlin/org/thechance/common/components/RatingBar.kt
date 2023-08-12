package org.thechance.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
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
    if (rating > count) throw Exception("rating is bigger than count")
    Row(modifier = modifier) {
        repeat(
            when {
                (rating.rem(1) <= 0.9) -> {
                    floor(rating).toInt()
                }

                else -> ceil(rating).toInt()
            }
        ) {
            Image(
                painter = selectedIcon,
                contentDescription = null,
                modifier = Modifier.size(iconsSize)
            )
        }
        if (rating.rem(1) in 0.5..0.9) {
            println(rating)
            Image(
                painter = halfSelectedIcon,
                contentDescription = null,
                modifier = Modifier.size(iconsSize)
            )
        }
        repeat(
            when {
                (rating.rem(1) >= 0.5) -> {
                    (count - ceil(rating)).toInt()
                }

                else -> (count - floor(rating)).toInt()
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