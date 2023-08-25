package org.thechance.common.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.beepbeep.designSystem.ui.theme.Theme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.thechance.common.presentation.composables.scaffold.BpSideBarItem
import org.thechance.common.presentation.composables.scaffold.DashBoardScaffold
import org.thechance.common.presentation.composables.scaffold.DashboardAppbar
import org.thechance.common.presentation.composables.scaffold.DashboardSideBar

object MainContainer : Screen, KoinComponent {

    private val screenModel: MainScreenModel by inject()

    @Composable
    override fun Content() {
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        TabNavigator(OverviewTab) {
            MainContent(
                state = state,
                onClickLogout = {
                    screenModel.logout()
                    navigator.popUntilRoot()
                }
            )
        }
    }

    @Composable
    private fun MainContent(
        state: MainUiState,
        onClickLogout: () -> Unit,
    ) {
        val tabNavigator = LocalTabNavigator.current

        DashBoardScaffold(
            appbar = {
                DashboardAppbar(
                    title = tabNavigator.current.options.title,
                    username = state.username,
                    onLogOut = onClickLogout,
                    isDropMenuExpanded = state.isDropMenuExpanded,
                    onClickDropDownMenu = screenModel::onClickDropDownMenu,
                    onDismissDropDownMenu = screenModel::onDismissDropDownMenu
                )
            },
            sideBar = {
                DashboardSideBar(
                    currentItem = tabNavigator.current.options.index.toInt()
                ) { sideBarUnexpandedWidthInKms ,mainMenuIsExpanded, itemHeight ->
                    TabNavigationItem(
                        tab = OverviewTab,
                        selectedIconResource = "ic_overview_fill.svg",
                        unSelectedIconResource = "ic_overview_empty.svg",
                        mainMenuIsExpanded = mainMenuIsExpanded,
                        sideBarUnexpandedWidth = sideBarUnexpandedWidthInKms,
                        modifier = Modifier.onGloballyPositioned {
                            itemHeight(it.boundsInParent().height)
                        }
                    )
                    TabNavigationItem(
                        tab = TaxisTab,
                        selectedIconResource = "ic_taxi_fill.svg",
                        unSelectedIconResource = "ic_taxi_empty.xml",
                        mainMenuIsExpanded = mainMenuIsExpanded,
                        sideBarUnexpandedWidth = sideBarUnexpandedWidthInKms,
                        modifier = Modifier.onGloballyPositioned {
                            itemHeight(it.boundsInParent().height)
                        }
                    )
                    TabNavigationItem(
                        tab = RestaurantsTab,
                        selectedIconResource = "ic_restaurant_fill.svg",
                        unSelectedIconResource = "ic_restaurant_empty.svg",
                        mainMenuIsExpanded = mainMenuIsExpanded,
                        sideBarUnexpandedWidth = sideBarUnexpandedWidthInKms,
                        modifier = Modifier.onGloballyPositioned {
                            itemHeight(it.boundsInParent().height)
                        }
                    )
                    TabNavigationItem(
                        tab = UsersTab,
                        selectedIconResource = "ic_users_fill.svg",
                        unSelectedIconResource = "ic_users_empty.svg",
                        mainMenuIsExpanded = mainMenuIsExpanded,
                        sideBarUnexpandedWidth = sideBarUnexpandedWidthInKms,
                        modifier = Modifier.onGloballyPositioned {
                            itemHeight(it.boundsInParent().height)
                        }
                    )
                }
            },
            content = {
                Box(Modifier.background(Theme.colors.surface).padding(it)) {
                    CurrentTab()
                }
            },
        )
    }

}

@Composable
fun ColumnScope.TabNavigationItem(
    tab: Tab,
    selectedIconResource: String,
    unSelectedIconResource: String,
    mainMenuIsExpanded: Boolean,
    sideBarUnexpandedWidth: Dp,
    modifier: Modifier = Modifier,
) {
    val tabNavigator = LocalTabNavigator.current
    BpSideBarItem(
        onClick = { tabNavigator.current = tab },
        isSelected = tabNavigator.current == tab,
        label = tab.options.title,
        selectedIconResource = selectedIconResource,
        unSelectedIconResource = unSelectedIconResource,
        mainMenuIsExpanded = mainMenuIsExpanded,
        sideBarUnexpandedWidthInKms = sideBarUnexpandedWidth,
        modifier = modifier
    )
}

