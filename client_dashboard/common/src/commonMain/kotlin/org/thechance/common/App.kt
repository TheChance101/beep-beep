package org.thechance.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.beepbeep.designSystem.ui.theme.BpTheme
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.di.initKoin
import org.thechance.common.ui.composables.modifier.centerItem
import org.thechance.common.ui.composables.modifier.cursorHoverIconHand
import org.thechance.common.ui.scaffold.DashboardSideBar
import org.thechance.common.ui.screen.main.OverviewTab
import org.thechance.common.ui.screen.main.RestaurantsTab
import org.thechance.common.ui.screen.main.TaxisTab
import org.thechance.common.ui.screen.main.UsersTab


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    initKoin()
    BpTheme {
        Navigator(HomeContent) {
            SlideTransition(it)
        }
    }

}

object HomeContent : Screen {
    @Composable
    override fun Content() {
        TabNavigator(OverviewTab) { navigator ->
            val tabNavigator = LocalTabNavigator.current
            DashboardSideBar(currentItem = tabNavigator.current.options.index.toInt()) { sideBarWidth, mainMenuIsExpanded,itemHeight ->

                ScaffoldTabNavigation(
                    tab = OverviewTab,
                    selectedIconResource = "ic_overview_fill.svg",
                    unSelectedIconResource = "ic_overview_empty.svg",
                    sideBarWidth = sideBarWidth,
                    mainMenuIsExpanded = mainMenuIsExpanded,
                    modifier = Modifier.onGloballyPositioned {
                        itemHeight(it.boundsInParent().height)
                    }
                )
                ScaffoldTabNavigation(
                    tab = TaxisTab,
                    selectedIconResource = "ic_taxi_fill.svg",
                    unSelectedIconResource = "ic_taxi_empty.xml",
                    sideBarWidth = sideBarWidth,
                    mainMenuIsExpanded = mainMenuIsExpanded,
                    modifier = Modifier.onGloballyPositioned {
                        itemHeight(it.boundsInParent().height)
                    }
                )
                ScaffoldTabNavigation(
                    tab = RestaurantsTab,
                    selectedIconResource = "ic_restaurant_fill.svg",
                    unSelectedIconResource = "ic_restaurant_empty.svg",
                    sideBarWidth = sideBarWidth,
                    mainMenuIsExpanded = mainMenuIsExpanded,
                    modifier = Modifier.onGloballyPositioned {
                        itemHeight(it.boundsInParent().height)
                    }
                )
                ScaffoldTabNavigation(
                    tab = UsersTab,
                    selectedIconResource = "ic_users_fill.svg",
                    unSelectedIconResource = "ic_users_empty.svg",
                    sideBarWidth = sideBarWidth,
                    mainMenuIsExpanded = mainMenuIsExpanded,
                    modifier = Modifier.onGloballyPositioned {
                        itemHeight(it.boundsInParent().height)
                    }
                )
            }
        }
    }
}


@Composable
fun ColumnScope.ScaffoldTabNavigation(
    tab: Tab,
    selectedIconResource: String,
    unSelectedIconResource: String,
    sideBarWidth: Dp,
    mainMenuIsExpanded: Boolean,
    modifier: Modifier = Modifier
) {
    val tabNavigator = LocalTabNavigator.current
    BpSideBarItem(
        onClick = { tabNavigator.current = tab },
        isSelected = tabNavigator.current == tab,
        itemWidth = 24.dp,
        label = tab.options.title,
        selectedIconResource = selectedIconResource,
        unSelectedIconResource = unSelectedIconResource,
        sideBarWidth = sideBarWidth,
        mainMenuIsExpanded = mainMenuIsExpanded,
        modifier = modifier
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.BpSideBarItem(
    onClick: () -> Unit,
    isSelected: Boolean,
    selectedIconResource: String,
    unSelectedIconResource: String,
    mainMenuIsExpanded: Boolean,
    sideBarWidth: Dp,
    itemWidth: Dp,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.weight(1f).fillMaxWidth().onGloballyPositioned {
//            mainMenuItemHeight.value = it.boundsInParent().height
        }.onClick(onClick = onClick).cursorHoverIconHand()
    ) {
        Icon(
            painterResource(if (isSelected) selectedIconResource else unSelectedIconResource),
            contentDescription = null,
            tint = if (isSelected) Theme.colors.primary else Theme.colors.contentSecondary,
            modifier = Modifier.size(24.dp)
                .centerItem(
                    targetState = mainMenuIsExpanded,
                    parentWidth = sideBarWidth,
                    itemWidth = itemWidth,
                    tween = tween(600)
                )
        )
        AnimatedVisibility(
            visible = mainMenuIsExpanded,
            enter = fadeIn(tween(800)),
            exit = fadeOut()
        ) {
            Text(
                label,
                style = Theme.typography.headline,
                color =
                if (isSelected) Theme.colors.primary
                else Theme.colors.contentSecondary,
                maxLines = 1,
                modifier = Modifier.padding(start = itemWidth)
            )
        }
    }
}