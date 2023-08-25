package presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
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
        val screenModel = rememberScreenModel{ MainScreenModel(restaurantId) }
        initScreen(screenModel)
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun onRender(state: MainScreenUIState, listener: MainScreenInteractionListener) {
        val options = rememberOptions()
        val charts = rememberChartData(state.charts)

        Column(Modifier.fillMaxSize().background(Theme.colors.background)) {
            HomeAppBar(
                onRestaurantSelect = listener::onRestaurantClick,
                onShowMenu = listener::onShowMenu,
                onDismissMenu = listener::onDismissMenu,
                restaurantName = state.restaurantName,
                state = state.isOpen,
                expanded = state.expanded,
                restaurants = state.restaurants
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(300.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = Theme.dimens.space8,
                    start = Theme.dimens.space16,
                    end = Theme.dimens.space16,
                    bottom = Theme.dimens.space16,
                ),
                verticalArrangement = Arrangement.spacedBy(Theme.dimens.space16),
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    FlowRow(
                        Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8),
                    ) {
                        options.forEach { option ->
                            OptionCardItem(
                                onClick = { listener.onCardClick(option.index) },
                                title = option.title,
                                imagePath = option.imagePath,
                                color = option.color,
                                modifier = Modifier.padding(top = Theme.dimens.space8),
                            )
                        }
                    }
                }

                items(charts) { chart ->
                    ChartItem(
                        imagePainter = chart.first,
                        chartItemUiState = chart.second,
                        sign = chart.second.sign,
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
            is MainScreenUIEffect.NavigateToOrdersHistory -> navigator.push(
                OrdersHistoryScreen(
                    effect.restaurantId
                )
            )

            is MainScreenUIEffect.NavigateToRestaurantInfo -> navigator.push(RestaurantInfoScreen())
        }
    }

    @Composable
    private fun rememberOptions(): List<OptionItemUiState> {
        val strings = Resources.strings
        val images = Resources.images
        val colors = Theme.colors

        return rememberSaveable {
            listOf(
                OptionItemUiState(
                    title = strings.allMeals,
                    imagePath = images.meals,
                    color = colors.orange,
                    index = 0,
                ),
                OptionItemUiState(
                    title = strings.orders,
                    imagePath = images.ordersBig,
                    color = colors.pink,
                    index = 1,
                ),
                OptionItemUiState(
                    title = strings.restaurantInfo,
                    imagePath = images.info,
                    color = colors.blue,
                    index = 2,
                ),
                OptionItemUiState(
                    title = strings.ordersHistory,
                    imagePath = images.ordersHistory,
                    color = colors.green,
                    index = 3,
                ),
            )
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun rememberChartData(charts: List<ChartItemUiState>): List<Pair<Painter, ChartItemUiState>> {
        val revenuePainterResource = painterResource(Resources.images.revenue)
        val ordersPainterResource = painterResource(Resources.images.orders)

        return rememberSaveable {
            charts.map { chartData ->
                Pair(
                    if (chartData.isRevenue) revenuePainterResource else ordersPainterResource,
                    chartData
                )
            }
        }
    }
}
