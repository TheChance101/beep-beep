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
import org.thechance.common.presentation.resources.Resources

object MainContainer : Screen, KoinComponent {

    private fun readResolve(): Any = MainContainer
    private val screenModel: MainScreenModel by inject()

    @Composable
    override fun Content() {
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val isDarkMode = screenModel.state.collectAsState().value.isDarkMode

        TabNavigator(OverviewTab) {
            MainContent(
                state = state,
                onClickLogout = {
                    screenModel.logout()
                    navigator.popUntilRoot()
                },
                onSwitchTheme = screenModel::onSwitchTheme,
                isDarkMode = isDarkMode
            )
        }
    }

    @Composable
    private fun MainContent(
        onClickLogout: () -> Unit,
        onSwitchTheme: () -> Unit,
        state: MainUiState,
        isDarkMode: Boolean
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
                    currentItem = tabNavigator.current.options.index.toInt(),
                    onSwitchTheme = onSwitchTheme,
                    darkTheme = isDarkMode,
                ) { sideBarUnexpandedWidthInKms, mainMenuIsExpanded, itemHeight ->
                    TabNavigationItem(
                        tab = OverviewTab,
                        selectedIconResource = Resources.Drawable.overviewFilled,
                        unSelectedIconResource = Resources.Drawable.overviewOutlined,
                        mainMenuIsExpanded = mainMenuIsExpanded,
                        sideBarUnexpandedWidth = sideBarUnexpandedWidthInKms,
                        modifier = Modifier.onGloballyPositioned {
                            itemHeight(it.boundsInParent().height)
                        }
                    )
                    TabNavigationItem(
                        tab = TaxisTab,
                        selectedIconResource = Resources.Drawable.taxiFilled,
                        unSelectedIconResource = Resources.Drawable.taxiOutlined,
                        mainMenuIsExpanded = mainMenuIsExpanded,
                        sideBarUnexpandedWidth = sideBarUnexpandedWidthInKms,
                        modifier = Modifier.onGloballyPositioned {
                            itemHeight(it.boundsInParent().height)
                        }
                    )
                    TabNavigationItem(
                        tab = RestaurantsTab,
                        selectedIconResource = Resources.Drawable.restaurantFilled,
                        unSelectedIconResource = Resources.Drawable.restaurantOutlined,
                        mainMenuIsExpanded = mainMenuIsExpanded,
                        sideBarUnexpandedWidth = sideBarUnexpandedWidthInKms,
                        modifier = Modifier.onGloballyPositioned {
                            itemHeight(it.boundsInParent().height)
                        }
                    )
                    TabNavigationItem(
                        tab = UsersTab,
                        selectedIconResource = Resources.Drawable.usersFilled,
                        unSelectedIconResource = Resources.Drawable.usersOutlined,
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

