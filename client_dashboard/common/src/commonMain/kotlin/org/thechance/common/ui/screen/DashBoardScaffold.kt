package org.thechance.common.ui.screen

import DropdownMenuNoPaddingVeitical
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beepbeep.designSystem.ui.composable.BpToggleButton
import org.thechance.common.ui.composables.Logo
import java.awt.Cursor


data class MainMenuUiState(
    val enabledIconResource: String = "ic_users_fill.svg",
    val disabledIconResource: String = "ic_users_empty.svg",
    val label: String = ""
)

val mainMenuItems = listOf(
    MainMenuUiState(
        enabledIconResource = "ic_overview_fill.svg",
        disabledIconResource = "ic_overview_empty.svg",
        label = "Overview"
    ),
    MainMenuUiState(
        enabledIconResource = "ic_taxi_fill.svg",
        disabledIconResource = "ic_taxi_empty.xml",
        label = "Taxis"
    ),
    MainMenuUiState(
        enabledIconResource = "ic_restaurant_fill.svg",
        disabledIconResource = "ic_restaurant_empty.svg",
        label = "Restaurants"
    ),
    MainMenuUiState(
        enabledIconResource = "ic_users_fill.svg",
        disabledIconResource = "ic_users_empty.svg",
        label = "Users"
    )

)

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DashBoardScaffold() {
    val isDropMenuExpanded = remember { mutableStateOf(false) }
    val mainMenuItemSize = remember { mutableStateOf(0f) }
    val mainMenuCurrentItem = remember { mutableStateOf(0) }
    val isExpanded = remember { mutableStateOf(false) }

    val animatedFloat = animateFloatAsState(targetValue = when(isExpanded.value){
        true -> .16f
        false -> .08f
    }
    )
    val sideBarWidth = remember { mutableStateOf(0f) }
    Row(Modifier.fillMaxSize()) {
        //region sideBar
        Column(
            Modifier.fillMaxHeight()
                .fillMaxWidth(animatedFloat.value).onGloballyPositioned {
                    sideBarWidth.value = it.boundsInParent().width
                }
                .background(Color(0xffFAFAFA)).drawBehind {
                drawEndBorder(strokeWidth = .5f, color = Color.Black.copy(.08f))
            }.padding(vertical = 40.dp).onPointerEvent(PointerEventType.Enter) {
                isExpanded.value = true
            }.onPointerEvent(PointerEventType.Exit) { isExpanded.value = false },
            horizontalAlignment = Alignment.Start
        ) {

            Box(Modifier.fillMaxWidth().offset(x = animateDpAsState(targetValue = if (!isExpanded.value) sideBarWidth.value.pxToDp() /2 - 12.dp else 24.dp,
                tween(600)
            ).value)){
                Logo(
                    expanded = isExpanded.value,
                )
            }
            Spacer(Modifier.fillMaxHeight(.09f))
            Box(Modifier.height(200.dp)) {
                Column(Modifier.fillMaxSize()) {
                    Spacer(Modifier.height(animateDpAsState((mainMenuItemSize.value.pxToDp() * mainMenuCurrentItem.value)).value))
                    Spacer(
                        Modifier.height(mainMenuItemSize.value.pxToDp())
                            .width(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xffF53D47))
                    )
                }
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    mainMenuItems.onEachIndexed { index, item ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f).fillMaxWidth().onGloballyPositioned {
                                mainMenuItemSize.value = it.boundsInParent().height
                            }.onClick { mainMenuCurrentItem.value = index }.cursorIcon()
                        ) {
                            Icon(
                                painterResource(if (mainMenuCurrentItem.value == index) item.enabledIconResource else item.disabledIconResource),
                                contentDescription = null,
                                tint = if (mainMenuCurrentItem.value == index) Color(0xffF53D47) else Color(
                                    0xff1F0000
                                ),
                                modifier = Modifier.size(24.dp).offset(x = animateDpAsState(targetValue = if (!isExpanded.value) sideBarWidth.value.pxToDp() /2 - 12.dp else 24.dp,
                                    tween((500))
                                ).value)
                            )
                            AnimatedVisibility(
                                visible = isExpanded.value,
                                enter = fadeIn(tween(600)),
                                exit = fadeOut()
                            ) {
                                Text(
                                    item.label,
                                    color = if (mainMenuCurrentItem.value == index) Color(0xffF53D47) else Color(
                                        0xff1F0000
                                    ),
                                    maxLines = 1,
                                    modifier = Modifier.padding(start = 24.dp)
                                )
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            Row (horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically){
                BpToggleButton(onToggle = {}, modifier = Modifier.offset(x=animateDpAsState(targetValue = if (!isExpanded.value) sideBarWidth.value.pxToDp() /2 - 32.dp else 32.dp -24.dp,
                    tween(600)
                ).value))
                AnimatedVisibility(
                    visible = isExpanded.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ){
                    Text("Dark theme", maxLines = 1)
                }

            }
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
                    }.cursorIcon()
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
                DropdownMenuNoPaddingVeitical(
                    expanded = isDropMenuExpanded.value,
                    onDismissRequest = { isDropMenuExpanded.value = false },
                    offset = DpOffset(x = 28.dp, y = 8.dp),
                    modifier = Modifier.background(Color.White)
                ) {
                    DropdownMenuItem(
                        onClick = { isDropMenuExpanded.value = false },
                        modifier = Modifier.cursorIcon(),
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

@Composable
fun Float.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

@Stable
fun Modifier.cursorIcon() =
    pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))

