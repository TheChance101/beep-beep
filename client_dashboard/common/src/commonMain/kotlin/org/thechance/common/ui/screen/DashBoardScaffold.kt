package org.thechance.common.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beepbeep.designSystem.ui.composable.BeepBeepToggleButton
import com.beepbeep.designSystem.ui.theme.surfaceTintLight

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashBoardScaffold() {
    val isDropMenuExpanded = remember { mutableStateOf(false) }
    Row(Modifier.fillMaxSize()) {
        //region sideBar
        Column(
            Modifier.fillMaxHeight().weight(.35f).background(Color(0xffFAFAFA)).drawBehind {
                drawEndBorder(strokeWidth = .5f, color = Color.Black.copy(.08f))
            }.padding(vertical = 40.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(Modifier.padding(start = 24.dp).size(75.dp).weight(.5f)) {
                Image(
                    painterResource("ic_beepbeep_logo_expanded.svg"),
                    contentDescription = null,
                    modifier = Modifier
                )
            }
            Box(Modifier.weight(.8f)) {
                Spacer(
                    Modifier
                        .width(4.dp)
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xffF53D47))
                )
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight().padding(start = 24.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(painterResource("ic_overview_empty.svg"), contentDescription = null)
                        Text("Overview")
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(painterResource("ic_taxi_empty.xml"), contentDescription = null, tint = Color(0xff1F0000))
                        Text("Taxis")
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(painterResource("ic_resaurant_empty.svg"), contentDescription = null)
                        Text("Resaurants")
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(painterResource("ic_users_empty.svg"), contentDescription = null)
                        Text("Users")
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            BeepBeepToggleButton(onToggle = {}, modifier = Modifier.padding(start = 24.dp))
        }
        //endregion

        //region Appbar
        Row(
            Modifier.height(96.dp).weight(1.5f)
                .bottomBorder(color = Color.Black.copy(.08f), strokeWidth = 2f)
                .padding(horizontal = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Users")
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.onClick {
                        isDropMenuExpanded.value = !isDropMenuExpanded.value
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .circleLayout()
                            .padding(8.dp),
                        fontSize = 16.sp,
                        text = "M",
                        color = Color.White
                    )
                    Text("Mohammed")
                    Image(
                        painter = painterResource("ic_drop_down_arrow.svg"),
                        contentDescription = null
                    )
                }
                DropdownMenu(
                    expanded = isDropMenuExpanded.value,
                    onDismissRequest = { isDropMenuExpanded.value = false },
                    offset = DpOffset(x = 28.dp, y = 8.dp),
                    modifier = Modifier.background(Color.White)
                        .border(BorderStroke(width = 1.dp, color = Color.Black.copy(alpha = .08f)))
                ) {
                    DropdownMenuItem(
                        onClick = { isDropMenuExpanded.value = false },
                        enabled = true,
                        text = {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Image(
                                    painterResource("ic_logout.svg"),
                                    contentDescription = null
                                )
                                Text(
                                    text = "Logout",
                                    textAlign = TextAlign.Center,
                                    color = Color(0xffF53D47)
                                )
                            }
                        }
                    )
                }
            }
        }
        //endregion
    }
}

@Stable
fun Modifier.circleLayout(color: Color = Color(0xffF53D47)) =
    background(color, shape = CircleShape)
        .layout { measurable, constraints ->
            // Measure the composable
            val placeable = measurable.measure(constraints)

            //get the current max dimension to assign width=height
            val currentHeight = placeable.height
            val currentWidth = placeable.width
            val newDiameter = maxOf(currentHeight, currentWidth)

            //assign the dimension and the center position
            layout(newDiameter, newDiameter) {
                // Where the composable gets placed
                placeable.placeRelative(
                    (newDiameter - currentWidth) / 2,
                    (newDiameter - currentHeight) / 2
                )
            }
        }

@Stable
fun Modifier.bottomBorder(color: Color, strokeWidth: Float) = drawWithContent {
    drawContent()
    clipRect {
        drawLine(
            brush = SolidColor(color),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Square,
            start = Offset.Zero.copy(y = size.height),
            end = Offset(x = size.width, y = size.height)
        )
    }
}

private fun DrawScope.drawEndBorder(
    strokeWidth: Float,
    color: Color,
    shareTop: Boolean = true,
    shareBottom: Boolean = true
) {
    if (strokeWidth == 0f) return
    drawPath(
        Path().apply {
            val width = size.width
            val height = size.height
            moveTo(width, 0f)
            lineTo(width - strokeWidth, if (shareTop) strokeWidth else 0f)
            lineTo(width - strokeWidth, if (shareBottom) height - strokeWidth else height)
            lineTo(width, height)
            close()
        },
        color = color
    )
}