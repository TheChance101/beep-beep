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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpToggleButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.ui.composables.Logo
import org.thechance.common.ui.composables.modifier.cursorHoverIconHand
import org.thechance.common.ui.composables.pxToDp


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
fun DashBoardScaffold(
    title: String,
    username: String,
    onLogOut: () -> Unit,
    content: @Composable (Dp) -> Unit,
) {
    val isDropMenuExpanded = remember { mutableStateOf(false) }
    val dropMenuArrowRotateDirection =
        animateFloatAsState(targetValue = if (isDropMenuExpanded.value) 180f else 0f)
    val mainMenuItemSize = remember { mutableStateOf(0f) }
    val mainMenuCurrentItem = remember { mutableStateOf(0) }
    val mainMenuIsExpanded = remember { mutableStateOf(false) }
    val mainMenuAnimatedWidth = animateFloatAsState(
        targetValue = if (mainMenuIsExpanded.value) .16f else .08f
    )
    val sideBarWidth = remember { mutableStateOf(0f) }
    val sideBarPadding by remember { mutableStateOf(24.dp) }

    Row(Modifier.fillMaxSize()) {
        //region sideBar
        Column(
            Modifier.fillMaxHeight()
                .fillMaxWidth(mainMenuAnimatedWidth.value).onGloballyPositioned {
                    sideBarWidth.value = it.boundsInParent().width
                }
                .background(Color(0xffFAFAFA))
                .drawBehind {
                    drawEndBorder(strokeWidth = 1.dp, color = Color.Black.copy(.08f))
                }
                .padding(vertical = 40.dp).onPointerEvent(PointerEventType.Enter) {
                    mainMenuIsExpanded.value = true
                }
                .onPointerEvent(PointerEventType.Exit) { mainMenuIsExpanded.value = false },
        ) {
            //region logo
            Box(
                Modifier.fillMaxWidth()
                    .offset(
                        x = animateDpAsState(
                            targetValue =
                            if (!mainMenuIsExpanded.value) sideBarWidth.value.pxToDp() / 2
                                    - sideBarPadding / 2
                            else sideBarPadding,
                            tween(600)
                        ).value
                    )
            ) {
                Logo(
                    expanded = mainMenuIsExpanded.value,
                )
            }
            //endregion
            Spacer(Modifier.fillMaxHeight(.09f))
            //region main menu
            Box(Modifier.height(200.dp)) {
                Column(Modifier.fillMaxSize()) {
                    Spacer(
                        Modifier.height(
                            animateDpAsState(
                                (mainMenuItemSize.value.pxToDp() * mainMenuCurrentItem.value)
                            ).value
                        )
                    )
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
                            }.onClick { mainMenuCurrentItem.value = index }.cursorHoverIconHand()
                        ) {
                            Icon(
                                painterResource(
                                    if (mainMenuCurrentItem.value == index) item.enabledIconResource
                                    else item.disabledIconResource
                                ),
                                contentDescription = null,
                                tint =
                                if (mainMenuCurrentItem.value == index) Color(0xffF53D47)
                                else Color(0xff1F0000),
                                modifier = Modifier.size(24.dp).offset(
                                    x = animateDpAsState(
                                        targetValue =
                                        if (!mainMenuIsExpanded.value)
                                            sideBarWidth.value.pxToDp() / 2 - sideBarPadding / 2
                                        else sideBarPadding,
                                        tween(500)
                                    ).value
                                )
                            )
                            AnimatedVisibility(
                                visible = mainMenuIsExpanded.value,
                                enter = fadeIn(tween(800)),
                                exit = fadeOut()
                            ) {
                                Text(
                                    item.label,
                                    style = Theme.typography.headline,
                                    color =
                                    if (mainMenuCurrentItem.value == index) Theme.colors.primary
                                    else Theme.colors.contentSecondary,
                                    maxLines = 1,
                                    modifier = Modifier.padding(start = sideBarPadding)
                                )
                            }
                        }
                    }
                }
            }
            //endregion
            Spacer(Modifier.weight(1f))
            //region toggle theme button
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BpToggleButton(
                    onToggle = {}, modifier = Modifier.offset(
                        x = animateDpAsState(
                            targetValue = if (!mainMenuIsExpanded.value) sideBarWidth.value.pxToDp() / 2 - 32.dp else sideBarPadding,
                            tween(600)
                        ).value
                    )
                )
                AnimatedVisibility(
                    visible = mainMenuIsExpanded.value,
                    enter = fadeIn(tween(500)),
                    exit = fadeOut(tween(500))
                ) {
                    Text(
                        "Dark theme",
                        maxLines = 1,
                        style = Theme.typography.titleMedium,
                        color = Theme.colors.contentPrimary
                    )
                }

            }
            //endregion
        }
        //endregion

        //region Appbar
        Column {
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
            content(40.dp)
        }
        //endregion
    }
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

private fun DrawScope.drawEndBorder(
    strokeWidth: Dp,
    color: Color,
    shareTop: Boolean = true,
    shareBottom: Boolean = true
) {
    val strokeWidthPx = strokeWidth.toPx()
    if (strokeWidthPx == 0f) return
    drawPath(
        Path().apply {
            val width = size.width
            val height = size.height
            moveTo(width, 0f)
            lineTo(width - strokeWidthPx, if (shareTop) strokeWidthPx else 0f)
            lineTo(width - strokeWidthPx, if (shareBottom) height - strokeWidthPx else height)
            lineTo(width, height)
            close()
        },
        color = color
    )
}

