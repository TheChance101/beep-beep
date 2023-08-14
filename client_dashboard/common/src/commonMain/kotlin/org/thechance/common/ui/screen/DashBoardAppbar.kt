package org.thechance.common.ui.screen

import DropdownMenuNoPaddingVeitical
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.ui.composables.modifier.cursorHoverIconHand

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardAppbar(title: String, username: String, onLogOut: () -> Unit) {
    val isDropMenuExpanded = remember { mutableStateOf(false) }
    val dropMenuArrowRotateDirection =
        animateFloatAsState(targetValue = if (isDropMenuExpanded.value) 180f else 0f)

    Row(
        Modifier.height(96.dp).fillMaxWidth().background(Color.White).drawBehind {
            drawBottomBorder(
                strokeWidth = 1.dp,
                color = Color.Black.copy(.08f),
                shareStart = true,
                shareEnd = true
            )
        }
            .padding(horizontal = 40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = Theme.typography.headlineLarge,
            color = Theme.colors.contentPrimary
        )
        Box(contentAlignment = Alignment.CenterEnd) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.onClick {
                    isDropMenuExpanded.value = !isDropMenuExpanded.value
                }.cursorHoverIconHand()
            ) {
                Text(
                    modifier = Modifier.circleLayout().padding(8.dp),
                    style = Theme.typography.titleMedium,
                    text = username.first().uppercase(),
                    color = Color.White
                )
                Text(username)
                Image(
                    painter = painterResource("ic_drop_down_arrow.svg"),
                    contentDescription = null,
                    modifier = Modifier.graphicsLayer {
                        rotationZ = dropMenuArrowRotateDirection.value
                    }
                )
            }
            Box(contentAlignment = Alignment.CenterEnd) {
                DropdownMenuNoPaddingVeitical(
                    expanded = isDropMenuExpanded.value,
                    onDismissRequest = { isDropMenuExpanded.value = false },
                    offset = DpOffset(0.dp, 24.dp),
                    modifier = Modifier.background(Color.White)
                ) {
                    DropdownMenuItem(
                        onClick = {
                            isDropMenuExpanded.value = false
                            onLogOut()
                        },
                        modifier = Modifier.cursorHoverIconHand(),
                        enabled = true,
                        text = {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painterResource("ic_logout.svg"),
                                    contentDescription = null
                                )
                                Text(
                                    text = "Logout",
                                    textAlign = TextAlign.Center,
                                    style = Theme.typography.titleMedium,
                                    color = Theme.colors.primary
                                )
                            }
                        }
                    )
                }
            }
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