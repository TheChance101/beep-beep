package presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parameterArrayOf
import presentation.base.BaseScreen
import presentation.composable.HomeAppBar
import presentation.info.RestaurantInfoScreen
import presentation.main.composables.ChartItem
import presentation.main.composables.OptionCardItem
import presentation.meals.MealsScreen
import presentation.order.OrderScreen
import presentation.order.orderHistory.OrdersHistoryScreen
import resources.Resources

class MainScreen(private val restaurantId: String) :
    BaseScreen<MainScreenModel, MainScreenUIState, MainScreenUIEffect, MainScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel { parameterArrayOf(restaurantId) })
    }

    @OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: MainScreenUIState, listener: MainScreenInteractionListener) {
        var rowSize by remember { mutableStateOf(IntSize.Zero) }
        var screenSize by remember { mutableStateOf(IntSize.Zero) }
        val isPortrait = screenSize.height > screenSize.width

        Column(
            Modifier.fillMaxSize().background(Theme.colors.background).onSizeChanged { screenSize = it }
        ) {
            HomeAppBar(
                onRestaurantSelect = listener::onRestaurantClicked,
                onShowMenu = listener::onShowMenu,
                onDismissMenu = listener::onDismissMenu,
                restaurantName = state.restaurantName,
                state = state.isOpen,
                expanded = state.expanded,
                restaurants = state.restaurants,
                modifier = Modifier.background(Theme.colors.surface)
                    .border(width = 1.dp, color = Theme.colors.divider, shape = RectangleShape),
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(300.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    FlowRow(
                        Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                            .onSizeChanged { rowSize = it },
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        val rowWidth = with(LocalDensity.current) { rowSize.width.toDp() }
                        val cardSize = if (isPortrait) (rowWidth / 2 - 4.dp) else 170.dp
                        OptionCardItem(
                            onClick = listener::onAllMealsCardClicked,
                            title = Resources.strings.allMeals,
                            imagePath = Resources.images.meals,
                            color = Theme.colors.orange,
                            modifier = Modifier.width(cardSize).padding(top = 8.dp),
                        )
                        OptionCardItem(
                            onClick = listener::onOrdersCardClicked,
                            title = Resources.strings.orders,
                            imagePath = Resources.images.ordersBig,
                            color = Theme.colors.pink,
                            modifier = Modifier.width(cardSize).padding(top = 8.dp),
                        )
                        OptionCardItem(
                            onClick = listener::onRestaurantInfoCardClicked,
                            title = Resources.strings.restaurantInfo,
                            imagePath = Resources.images.info,
                            color = Theme.colors.blue,
                            modifier = Modifier.width(cardSize).padding(top = 8.dp),
                        )
                        OptionCardItem(
                            onClick = listener::onOrdersHistoryCardClicked,
                            title = Resources.strings.ordersHistory,
                            imagePath = Resources.images.ordersHistory,
                            color = Theme.colors.green,
                            modifier = Modifier.width(cardSize).padding(top = 8.dp),
                        )
                    }
                }

                item {
                    ChartItem(
                        imagePainter = painterResource(Resources.images.revenue),
                        chartItemUiState = state.revenueChart,
                    )
                }
                item {
                    ChartItem(
                        imagePainter = painterResource(Resources.images.orders),
                        chartItemUiState = state.ordersChart,
                    )
                }
            }
        }
    }

    override fun onEffect(effect: MainScreenUIEffect, navigator: Navigator) {
        when (effect) {
            is MainScreenUIEffect.Back -> navigator.pop()
            is MainScreenUIEffect.NavigateToAllMeals -> navigator.push(MealsScreen())
            is MainScreenUIEffect.NavigateToOrders -> navigator.push(OrderScreen())
            is MainScreenUIEffect.NavigateToRestaurantInfo -> navigator.push(RestaurantInfoScreen())
            is MainScreenUIEffect.NavigateToOrdersHistory -> navigator.push(
                OrdersHistoryScreen(effect.restaurantId)
            )
        }
    }
}
