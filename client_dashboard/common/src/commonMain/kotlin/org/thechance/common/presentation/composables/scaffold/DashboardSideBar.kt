package org.thechance.common.presentation.composables.scaffold

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpToggleButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.composables.BpLogo
import org.thechance.common.presentation.composables.modifier.centerItem
import org.thechance.common.presentation.composables.modifier.cursorHoverIconHand
import org.thechance.common.presentation.composables.pxToDp
import org.thechance.common.presentation.util.kms

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DashboardSideBar(
    onSwitchTheme: () -> Unit = {},
    darkTheme: Boolean = true,
    currentItem: Int,
    content: @Composable ColumnScope.(
        sideBarUnexpandedWidthInKms:Dp,
        mainMenuIsExpanded: Boolean,
        itemHeight: (itemHeight: Float) -> Unit
    ) -> Unit
) {
    val mainMenuItemHeight = remember { mutableStateOf(0f) }
    val mainMenuIsExpanded = remember { mutableStateOf(false) }
    val sideBarUnexpandedWidthInKms: Dp by remember { mutableStateOf(120.kms) }
    val sideBarExpandedWidthInKms: Dp by remember { mutableStateOf(300.kms) }

    Row(Modifier.fillMaxHeight().background(Theme.colors.background)) {
        //region sidebar
        Column(
            Modifier
                .width(
                    animateDpAsState(
                        targetValue = if (mainMenuIsExpanded.value) sideBarExpandedWidthInKms
                        else sideBarUnexpandedWidthInKms
                    ).value
                )
                .padding(vertical = 40.kms).onPointerEvent(PointerEventType.Enter) {
                    mainMenuIsExpanded.value = true
                }
                .onPointerEvent(PointerEventType.Exit) { mainMenuIsExpanded.value = false },
        ) {
            //region logo
            BpLogo(
                expanded = mainMenuIsExpanded.value,
                modifier = Modifier.centerItem(
                    sideBarUnexpandedWidthInDp = sideBarUnexpandedWidthInKms,
                    itemWidth = 36f
                )
            )
            //endregion
            Spacer(Modifier.fillMaxHeight(.1f))
            //region main menu
            Box(Modifier.height(sideBarExpandedWidthInKms)) {
                Column(Modifier.fillMaxSize()) {
                    Spacer(
                        Modifier.height(
                            animateDpAsState(
                                (mainMenuItemHeight.value.pxToDp() * currentItem)
                            ).value
                        )
                    )
                    Spacer(
                        Modifier.height(mainMenuItemHeight.value.pxToDp())
                            .padding(vertical = Theme.dimens.space8)
                            .width(4.dp)
                            .clip(RoundedCornerShape(Theme.dimens.space16))
                            .background(Color(0xffF53D47))
                    )
                }
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    content(sideBarUnexpandedWidthInKms,mainMenuIsExpanded.value) { itemHeight ->
                        mainMenuItemHeight.value = itemHeight
                    }
                }
            }
            //endregion
            Spacer(Modifier.weight(1f))
            //region toggle theme button
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.kms),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BpToggleButton(
                    isDark = darkTheme,
                    onToggle = onSwitchTheme::invoke,
                    modifier = Modifier
                        .centerItem(sideBarUnexpandedWidthInDp = sideBarUnexpandedWidthInKms)
                )
                AnimatedVisibility(
                    visible = mainMenuIsExpanded.value,
                    enter = fadeIn(tween(500)),
                    exit = fadeOut()
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

        //region end border
        Divider(
            color = Theme.colors.divider,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.kms)
        )
        //endregion
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.BpSideBarItem(
    onClick: () -> Unit,
    isSelected: Boolean,
    selectedIconResource: String,
    unSelectedIconResource: String,
    mainMenuIsExpanded: Boolean,
    sideBarUnexpandedWidthInKms: Dp,
    label: String,
    modifier: Modifier = Modifier,
) {
    val iconSize: Dp by remember { mutableStateOf(24.dp) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space16 + iconSize),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.weight(1f).fillMaxWidth().onClick(onClick = onClick)
            .cursorHoverIconHand()
    ) {
        Icon(
            painterResource(if (isSelected) selectedIconResource else unSelectedIconResource),
            contentDescription = null,
            tint = if (isSelected) Theme.colors.primary else Theme.colors.contentSecondary,
            modifier = Modifier.size(iconSize).graphicsLayer {
                translationX = (sideBarUnexpandedWidthInKms.toPx() - iconSize.toPx()) / 2
            }
        )
        AnimatedVisibility(
            visible = mainMenuIsExpanded,
            enter = fadeIn(tween(500)),
            exit = fadeOut(),
            modifier = Modifier.padding(end = Theme.dimens.space16)
        ) {
            Text(
                label,
                style = Theme.typography.headline,
                color = if (isSelected) Theme.colors.primary else Theme.colors.contentSecondary,
                maxLines = 1,
            )
        }
    }
}