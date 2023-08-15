package org.thechance.common.ui.scaffold

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpToggleButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.ui.composables.Logo
import org.thechance.common.ui.composables.modifier.border
import org.thechance.common.ui.composables.modifier.centerItem
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

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun DashboardSideBar(onSwitchTheme: () -> Unit, darkTheme: Boolean) {
    val mainMenuItemHeight = remember { mutableStateOf(0f) }
    var mainMenuCurrentItem by remember { mutableStateOf(0) }
    val iconSize by remember { mutableStateOf(24.dp) }
    val mainMenuIsExpanded = remember { mutableStateOf(false) }
    val sideBarWidth = remember { mutableStateOf(0f) }


    Column(
        Modifier.fillMaxHeight()
            .fillMaxWidth(
                animateFloatAsState(
                    targetValue = if (mainMenuIsExpanded.value) .16f else .08f
                ).value
            ).onGloballyPositioned { sideBarWidth.value = it.boundsInParent().width }
            .background(Theme.colors.background)
            .border(end = BorderStroke(width = 1.dp, color = Theme.colors.divider))
            .padding(vertical = 40.dp).onPointerEvent(PointerEventType.Enter) {
                mainMenuIsExpanded.value = true
            }
            .onPointerEvent(PointerEventType.Exit) { mainMenuIsExpanded.value = false },
    ) {
        //region logo
        Logo(
            expanded = mainMenuIsExpanded.value,
            modifier = Modifier.fillMaxWidth().centerItem(
                targetState = mainMenuIsExpanded.value,
                parentWidth = sideBarWidth.value.pxToDp(),
                itemWidth = iconSize,
                tween = tween(600)
            )
        )
        //endregion
        Spacer(Modifier.fillMaxHeight(.1f))
        //region main menu
        Box(Modifier.height(200.dp)) {
            Column(Modifier.fillMaxSize()) {
                Spacer(
                    Modifier.height(
                        animateDpAsState(
                            (mainMenuItemHeight.value.pxToDp() * mainMenuCurrentItem)
                        ).value
                    )
                )
                Spacer(
                    Modifier.height(mainMenuItemHeight.value.pxToDp())
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
                            mainMenuItemHeight.value = it.boundsInParent().height
                        }.onClick { mainMenuCurrentItem = index }.cursorHoverIconHand()
                    ) {
                        Icon(
                            painterResource(
                                if (mainMenuCurrentItem == index) item.enabledIconResource
                                else item.disabledIconResource
                            ),
                            contentDescription = null,
                            tint =
                            if (mainMenuCurrentItem == index) Color(0xffF53D47)
                            else Theme.colors.contentSecondary,
                            modifier = Modifier.size(24.dp)
                                .centerItem(
                                    targetState = mainMenuIsExpanded.value,
                                    parentWidth = sideBarWidth.value.pxToDp(),
                                    itemWidth = iconSize,
                                    tween = tween(600)
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
                                if (mainMenuCurrentItem == index) Theme.colors.primary
                                else Theme.colors.contentSecondary,
                                maxLines = 1,
                                modifier = Modifier.padding(start = iconSize)
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
                isDark = darkTheme,
                onToggle = onSwitchTheme::invoke, modifier = Modifier
                    .centerItem(
                        targetState = mainMenuIsExpanded.value,
                        parentWidth = sideBarWidth.value.pxToDp(),
                        itemWidth = 64.dp,
                        tween = tween(600)
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
}