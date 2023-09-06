package presentation.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.beepbeep.designSystem.ui.composable.BpNavigationBar
import com.beepbeep.designSystem.ui.composable.BpNavigationBarItem
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.composable.exitinstion.drawTopIndicator
import presentation.composable.exitinstion.toPx


object MainContainer : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        TabNavigator(HomeTab) {
            Scaffold(
                topBar = {
                    val tabNavigator = LocalTabNavigator.current
                    //Text(text = tabNavigator.current.options.title)
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

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun BottomBar(tabs: List<TabContainer>, tabNavigator: TabNavigator) {
        var xIndicatorOffset by remember { mutableStateOf(Float.NaN) }
        val xOffsetAnimated by animateFloatAsState(targetValue = xIndicatorOffset)
        val indicatorWidthPx = 40.dp.toPx()
        val iconSizePx = 24.dp.toPx()

        BpNavigationBar(modifier = Modifier.drawTopIndicator(xOffsetAnimated)) {
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
}