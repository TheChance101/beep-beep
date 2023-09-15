package presentation.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
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
import presentation.main.composables.OptionCardItem
import presentation.meals.MealsScreen
import presentation.order.OrderScreen
import presentation.order.orderHistory.OrdersHistoryScreen
import presentation.restaurantSelection.RestaurantUIState
import resources.Resources

class MainScreen(private val restaurantId: String) :
    BaseScreen<MainScreenModel, MainScreenUIState, MainScreenUIEffect, MainScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel { parametersOf(restaurantId) })
    }

    @OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: MainScreenUIState, listener: MainScreenInteractionListener) {
        var rowSize by remember { mutableStateOf(IntSize.Zero) }
        var screenSize by remember { mutableStateOf(IntSize.Zero) }
        val isPortrait = screenSize.height > screenSize.width

        Column(
            Modifier.fillMaxSize().background(Theme.colors.background)
                .onSizeChanged { screenSize = it }
        ) {

            AppBarDropDownLeading(
                onRestaurantSelect = listener::onRestaurantClicked,
                onShowMenu = listener::onShowMenu,
                onDismissMenu = listener::onDismissMenu,
                restaurantName = state.selectedRestaurant.restaurantName,
                state = state.selectedRestaurant.isOpen,
                expanded = state.expanded,
                restaurants = state.restaurants,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colors.surface)
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
                        val cardSize = if (isPortrait) ((rowWidth / 2 )- 4.dp) else 170.dp
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

    @Composable
    fun AppBarDropDownLeading(
        onRestaurantSelect: (String) -> Unit,
        onShowMenu: () -> Unit,
        onDismissMenu: () -> Unit,
        state: Boolean,
        restaurantName: String,
        expanded: Boolean,
        restaurants: List<RestaurantUIState>,
        modifier: Modifier = Modifier,
    ) {
        val buttonBackgroundColor by animateColorAsState(if (state) Theme.colors.hover else Color.Transparent)
        val buttonContentColor by animateColorAsState(if (state) Theme.colors.primary else Theme.colors.disable)

        Column(modifier = modifier.fillMaxWidth()) {
            Row(
                modifier = modifier.fillMaxWidth().padding(Theme.dimens.space16),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                MultipleRestaurants(
                    onClick = onShowMenu,
                    restaurantName = restaurantName,
                    isMultipleRestaurants = restaurants.isNotEmpty()
                )

                BpTransparentButton(
                    modifier = Modifier.background(buttonBackgroundColor),
                    title = if (state) Resources.strings.open else Resources.strings.closed,
                    enabled = false,
                    contentColor = buttonContentColor,
                    onClick = {}
                )
            }

            BpDropdownMenu(
                expanded = expanded,
                modifier = Modifier.heightIn(max = 350.dp)
                    .widthIn(min = 260.dp),
                offset = DpOffset(Theme.dimens.space16, 0.dp),
                onDismissRequest = onDismissMenu
            ) {
                restaurants.forEach { restaurant ->
                    RestaurantInformation(
                        onRestaurantClick = { onRestaurantSelect(restaurant.id) },
                        restaurantName = restaurant.restaurantName,
                        restaurantNumber = restaurant.restaurantNumber,
                        isOpen = restaurant.isOpen
                    )
                }
            }
        }
    }


    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun MultipleRestaurants(
        onClick: () -> Unit,
        restaurantName: String,
        modifier: Modifier = Modifier,
        isMultipleRestaurants: Boolean
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

            if (isMultipleRestaurants) {
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
            is MainScreenUIEffect.NavigateToOrders -> navigator.push(OrderScreen())
            is MainScreenUIEffect.NavigateToRestaurantInfo -> navigator.push(
                RestaurantInformationScreen(effect.restaurantId)
            )

            is MainScreenUIEffect.NavigateToOrdersHistory -> navigator.push(
                OrdersHistoryScreen(effect.restaurantId)
            )
        }
    }
}
