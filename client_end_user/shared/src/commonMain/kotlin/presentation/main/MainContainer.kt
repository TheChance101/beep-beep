package presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.Resources


object MainContainer : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        TabNavigator(HomeTab) {
            Scaffold(
                topBar = {
                    val tabNavigator = LocalTabNavigator.current
                    Text(text = tabNavigator.current.options.title)
                },
                bottomBar = {
                    MainBottomBar()
                },
                content = {
                    Column(Modifier.fillMaxSize().padding(it)) {
                        CurrentTab()
                    }
                }
            )
        }
    }

    @Composable
    private fun MainBottomBar(){
        val tabNavigator = LocalTabNavigator.current
        var selectedItemPositionPx by remember { mutableStateOf(0f) }
        val selectedItemPositionAnimated by animateFloatAsState(selectedItemPositionPx)
        val colors = Theme.colors

        Row(
            Modifier.fillMaxWidth().height(64.dp).background(colors.surface)
                .drawBehind {
                    drawLine(
                        color = colors.divider,
                        strokeWidth = 1.dp.toPx(),
                        start = Offset.Zero,
                        end = Offset.Zero.copy(x = size.width)
                    )
                    drawLine(
                        color = colors.primary,
                        strokeWidth = 1.dp.toPx(),
                        start = Offset.Zero.copy(x = selectedItemPositionAnimated + 4.dp.toPx()),
                        end = Offset.Zero.copy(x = selectedItemPositionAnimated + 40.dp.toPx() + 4.dp.toPx()),
                        cap = StrokeCap.Round
                    )
                }.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TabNavigationItem(
                tab = HomeTab,
                selectedIconResource = Resources.images.homeFilled,
                unSelectedIconResource = Resources.images.homeOutlined,
                modifier = Modifier.weight(1f),
                onSelectChange = { tab -> tabNavigator.current = tab },
                onPositioned = { selectedItemPositionPx = it }
            )
            TabNavigationItem(
                tab = SearchTab,
                selectedIconResource = Resources.images.searchFilled,
                unSelectedIconResource = Resources.images.searchOutlined,
                modifier = Modifier.weight(1f),
                onSelectChange = { tab -> tabNavigator.current = tab },
                onPositioned = { selectedItemPositionPx = it }
            )
            TabNavigationItem(
                tab = OrdersTab,
                selectedIconResource = Resources.images.ordersFilled,
                unSelectedIconResource = Resources.images.ordersOutlined,
                modifier = Modifier.weight(1f),
                onSelectChange = { tab -> tabNavigator.current = tab },
                onPositioned = { selectedItemPositionPx = it }
            )
            TabNavigationItem(
                tab = NotificationsTab,
                selectedIconResource = Resources.images.notificationsFilled,
                unSelectedIconResource = Resources.images.notificationsOutlined,
                modifier = Modifier.weight(1f),
                onSelectChange = { tab -> tabNavigator.current = tab },
                onPositioned = { selectedItemPositionPx = it }
            )
            TabNavigationItem(
                tab = ProfileTab,
                selectedIconResource = Resources.images.profileFilled,
                unSelectedIconResource = Resources.images.profileOutlined,
                modifier = Modifier.weight(1f),
                onSelectChange = { tab -> tabNavigator.current = tab },
                onPositioned = { selectedItemPositionPx = it }
            )
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun RowScope.TabNavigationItem(
        tab: Tab,
        selectedIconResource: String,
        unSelectedIconResource: String,
        selected: Boolean = LocalTabNavigator.current.current == tab,
        onSelectChange: (Tab) -> Unit,
        onPositioned: (Float) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val drawable = if (selected) selectedIconResource else unSelectedIconResource
        val tint by animateColorAsState(if (selected) Theme.colors.primary else Theme.colors.contentTertiary)
        var itemPosition by remember { mutableStateOf(0f) }
        val iconSizePx = with(LocalDensity.current) { 24.dp.toPx() }

        Column(
            modifier.clickable(
                remember { MutableInteractionSource() },
                null
            ) { onSelectChange(tab); onPositioned(itemPosition) },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(drawable),
                tint = tint,
                contentDescription = tab.options.title,
                modifier = Modifier.size(24.dp).onGloballyPositioned {
                    itemPosition = it.positionInWindow().x - iconSizePx / 2
                    if (selected) {
                        onPositioned(itemPosition)
                    }
                }
            )
            AnimatedVisibility(visible = selected) {
                Text(text = tab.options.title, style = Theme.typography.caption.copy(color = tint))
            }
        }
    }
}