package org.thechance.common.ui.composables.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout

@Stable
fun Modifier.circleLayout(color: Color = Color(0xffF53D47)): Modifier {
    return then(
        background(color, shape = CircleShape)
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)
                val currentHeight = placeable.height
                val currentWidth = placeable.width
                val newDiameter = maxOf(placeable.height, placeable.width)

                layout(newDiameter, newDiameter) {
                    placeable.placeRelative(
                        (newDiameter - currentWidth) / 2,
                        (newDiameter - currentHeight) / 2
                    )
                }
            }
    )
}