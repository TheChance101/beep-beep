package presentation.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import presentation.base.BaseScreen
import presentation.composable.BpDropdownMenu
import presentation.composable.RestaurantInformation
import presentation.composable.modifier.noRippleEffect
import presentation.information.RestaurantInformationScreen
import presentation.main.composables.ChartItem
import presentation.main.composables.ChartsLoadingEffect
import presentation.main.composables.OptionCardItem
import presentation.meals.MealsScreen
import presentation.order.LocationUiSate
import presentation.order.OrderScreen
import presentation.orderHistory.OrdersHistoryScreen
import presentation.restaurantSelection.RestaurantUIState
import resources.Resources
import util.getNavigationBarPadding
import util.getStatusBarPadding
import util.toWeekDay

class MainScreen() :
    BaseScreen<MainScreenModel, MainScreenUIState, MainScreenUIEffect, MainScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun onRender(state: MainScreenUIState, listener: MainScreenInteractionListener) {
        var screenSize by remember { mutableStateOf(IntSize.Zero) }
        val isPortrait = screenSize.height > screenSize.width
        var rowSize by remember { mutableStateOf(IntSize.Zero) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background)
                .onSizeChanged { screenSize = it }
        ) {

            AppBarDropDownLeading(
                onSelectRestaurant = listener::onRestaurantClicked,
                onShowMenu = listener::onShowMenu,
                onDismissMenu = listener::onDismissMenu,
                restaurantName = state.selectedRestaurant.restaurantName,
                isRestaurantOpened = state.selectedRestaurant.isOpen,
                expanded = state.expanded,
                restaurants = state.restaurants,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colors.surface)
                    .padding(top = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(300.dp),
                modifier = Modifier.fillMaxSize()
                    .padding(bottom = getNavigationBarPadding().calculateBottomPadding()),
                contentPadding = PaddingValues(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 24.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    FlowRow(
                        Modifier.fillMaxWidth().onSizeChanged { rowSize = it },
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        val rowWidth = with(LocalDensity.current) { rowSize.width.toDp() }
                        val cardSize = if (isPortrait) ((rowWidth / 2) - 4.dp) else 170.dp
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

                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    AnimatedContent(state.isLoading) {
                        if (state.isLoading) {
                            ChartsLoadingEffect()
                        } else {
                            ChartsContent(state)
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class)
    @Composable
    private fun ChartsContent(state: MainScreenUIState) {

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            maxItemsInEachRow = 2
        ) {
            ChartItem(
                modifier = Modifier.fillMaxWidth().height(420.dp),
                imagePainter = painterResource(Resources.images.revenue),
                title = Resources.strings.revenue,
                total = state.totalOrderReturns
            ) {
                BarChart(
                    chartParameters = listOf(
                        BarParameters(
                            dataName = Resources.strings.revenue,
                            data = state.revenueStatistics.yAxisData,
                            barColor = Theme.colors.primary
                        )
                    ),
                    xAxisData = state.revenueStatistics.xAxisData.toWeekDay(),
                    legendPosition = LegendPosition.DISAPPEAR,
                    yAxisRange = 7,
                    barCornerRadius = 4.dp,
                    spaceBetweenBars = 4.dp,
                    barWidth = 16.dp,
                    yAxisStyle = Theme.typography.caption.copy(Theme.colors.contentTertiary),
                    xAxisStyle = Theme.typography.caption.copy(Theme.colors.contentTertiary),
                    gridColor = Theme.colors.divider,
                    spaceBetweenGroups = 20.dp
                )
            }

            ChartItem(
                modifier = Modifier.fillMaxWidth().height(420.dp),
                imagePainter = painterResource(Resources.images.orders),
                title = Resources.strings.orders,
                total = state.totalOrders.toString()
            ) {
                LineChart(
                    linesParameters = listOf(
                        LineParameters(
                            label = Resources.strings.orders,
                            data = state.ordersCountStatistics.yAxisData,
                            lineColor = Theme.colors.primary,
                            lineType = LineType.CURVED_LINE,
                            lineShadow = true,
                        )
                    ),
                    xAxisData = state.ordersCountStatistics.xAxisData.toWeekDay(),
                    legendPosition = LegendPosition.DISAPPEAR,
                    yAxisRange = 7,
                    yAxisStyle = Theme.typography.caption.copy(Theme.colors.contentTertiary),
                    xAxisStyle = Theme.typography.caption.copy(Theme.colors.contentTertiary),
                    gridColor = Theme.colors.divider,
                )
            }
        }

    }

    @Composable
    fun AppBarDropDownLeading(
        onSelectRestaurant: (restaurantId: String,location:LocationUiSate,address:String) -> Unit,
        onShowMenu: () -> Unit,
        onDismissMenu: () -> Unit,
        isRestaurantOpened: Boolean,
        restaurantName: String,
        expanded: Boolean,
        restaurants: List<RestaurantUIState>,
        modifier: Modifier = Modifier,
    ) {
        val buttonBackgroundColor by animateColorAsState(
            if (isRestaurantOpened) Theme.colors.hover else Color.Transparent
        )
        val buttonContentColor by animateColorAsState(
            if (isRestaurantOpened) Theme.colors.primary else Theme.colors.disable
        )
        val hasMultipleRestaurants = restaurants.size != 1

        Column(modifier = modifier.fillMaxWidth()) {
            Row(
                modifier = modifier.fillMaxWidth().padding(Theme.dimens.space16),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                MultipleRestaurants(
                    onClick = onShowMenu,
                    restaurantName = restaurantName,
                    hasMultipleRestaurants = hasMultipleRestaurants
                )

                BpTransparentButton(
                    modifier = Modifier
                        .clip(RoundedCornerShape(Theme.radius.medium))
                        .background(buttonBackgroundColor),
                    title =
                    if (isRestaurantOpened) Resources.strings.open else Resources.strings.closed,
                    enabled = false,
                    contentColor = buttonContentColor,
                    onClick = {}
                )
            }

            AnimatedVisibility(hasMultipleRestaurants) {
                BpDropdownMenu(
                    expanded = expanded,
                    modifier = Modifier.heightIn(max = 350.dp).widthIn(min = 260.dp),
                    offset = DpOffset(Theme.dimens.space16, 0.dp),
                    onDismissRequest = onDismissMenu
                ) {
                    restaurants.forEach { restaurant ->
                        RestaurantInformation(
                            onRestaurantClick = { onSelectRestaurant(restaurant.restaurantId,restaurant.location,restaurant.address) },
                            restaurantName = restaurant.restaurantName,
                            restaurantNumber = restaurant.restaurantPhoneNumber,
                            isOpen = restaurant.isOpen
                        )
                    }
                }
            }
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(Theme.colors.divider))
    }


    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun MultipleRestaurants(
        onClick: () -> Unit,
        restaurantName: String,
        modifier: Modifier = Modifier,
        hasMultipleRestaurants: Boolean,
    ) {
        Row(
            modifier = modifier.noRippleEffect(onClick),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = restaurantName,
                style = Theme.typography.titleLarge,
                color = Theme.colors.contentPrimary
            )

            AnimatedVisibility(hasMultipleRestaurants) {
                Icon(
                    painter = painterResource(Resources.images.arrowDown),
                    contentDescription = null,
                    tint = Theme.colors.contentPrimary
                )
            }

        }
    }

    override fun onEffect(effect: MainScreenUIEffect, navigator: Navigator) {
        when (effect) {
            is MainScreenUIEffect.Back -> navigator.pop()
            is MainScreenUIEffect.NavigateToAllMeals -> navigator.push(MealsScreen(effect.restaurantId))
            is MainScreenUIEffect.NavigateToOrders -> navigator.push(OrderScreen(effect.restaurantId))
            is MainScreenUIEffect.NavigateToRestaurantInfo -> navigator.push(
                RestaurantInformationScreen(effect.restaurantId)
            )

            is MainScreenUIEffect.NavigateToOrdersHistory -> navigator.push(
                OrdersHistoryScreen(effect.restaurantId)
            )
        }
    }
}