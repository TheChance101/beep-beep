package presentation.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.beepbeep.designSystem.ui.composable.BpAppBar
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpNavigationBar
import com.beepbeep.designSystem.ui.composable.BpNavigationBarItem
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composable.exitinstion.drawTopIndicator
import presentation.composable.exitinstion.toPx
import presentation.auth.login.LoginScreen
import resources.Resources
import util.getNavigationBarPadding


object MainContainer :
    BaseScreen<MainScreenModel, MainUiState, MainScreenUiEffect, MainInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun onRender(state: MainUiState, listener: MainInteractionListener) {
        TabNavigator(HomeTab) {
            Scaffold(
                topBar = {
                    BpAppBar(
                        title = if (state.isLogin) {
                            Resources.strings.welcome + " ${state.username}"
                        } else {
                            Resources.strings.loginWelcomeMessage
                        },
                        actions = {
                            if (state.isLogin) {
                                Wallet(value = state.currency + state.wallet)
                            } else {
                                BpButton(
                                    modifier = Modifier.heightIn(max = 32.dp).padding(end = 16.dp),
                                    textPadding = PaddingValues(horizontal = 16.dp),
                                    title = Resources.strings.login,
                                    onClick = listener::onLoginClicked
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    val tabNavigator = LocalTabNavigator.current
                    val tabs = rememberTabsContainer()
                    BottomBar(tabs, tabNavigator)
                },
                content = {
                    Column(Modifier.fillMaxSize().padding(it)) {
                        CurrentTab()
                    }
                }
            )
        }
    }

    override fun onEffect(effect: MainScreenUiEffect, navigator: Navigator) {
        when (effect) {
            is MainScreenUiEffect.NavigateLoginScreen -> navigator.push(LoginScreen())
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun BottomBar(tabs: List<TabContainer>, tabNavigator: TabNavigator) {
        var xIndicatorOffset by remember { mutableStateOf(Float.NaN) }
        val xOffsetAnimated by animateFloatAsState(targetValue = xIndicatorOffset)
        val indicatorWidthPx = 40.dp.toPx()
        val iconSizePx = 24.dp.toPx()

        BpNavigationBar(
            modifier = Modifier.background(Theme.colors.surface)
                .drawTopIndicator(xOffsetAnimated).padding(getNavigationBarPadding())
        ) {
            tabs.forEach { tabContainer ->
                val selected = tabNavigator.current == tabContainer.tab
                val drawable =
                    if (selected) tabContainer.selectedIcon else tabContainer.unSelectedIcon

                BpNavigationBarItem(
                    icon = { color ->
                        Icon(
                            painter = painterResource(drawable),
                            contentDescription = null,
                            tint = color,
                            modifier = Modifier.size(24.dp).onGloballyPositioned {
                                if (selected) {
                                    xIndicatorOffset =
                                        it.positionInRoot().x + (iconSizePx - indicatorWidthPx) / 2
                                }
                            }
                        )
                    },
                    label = { Text(text = tabContainer.tab.options.title, style = it) },
                    selected = selected,
                    onClick = { tabNavigator.current = tabContainer.tab },
                )
            }
        }
    }

    @Composable
    private fun Wallet(
        modifier: Modifier = Modifier,
        value: String
    ) {
        Column(modifier = modifier.padding(end = 16.dp)) {
            Text(
                Resources.strings.wallet,
                style = Theme.typography.body,
                color = Theme.colors.contentSecondary
            )
            Text(
                value,
                style = Theme.typography.title,
                color = Theme.colors.primary
            )
        }
    }
}
