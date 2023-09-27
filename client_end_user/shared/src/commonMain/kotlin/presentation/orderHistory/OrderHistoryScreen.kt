package presentation.orderHistory

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.base.BaseScreen
import presentation.composable.LoginRequiredPlaceholder

class OrderHistoryScreen :
    BaseScreen<OrderHistoryScreenModel, OrderScreenUiState, OrderHistoryScreenUiEffect, OrderHistoryScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: OrderHistoryScreenUiEffect, navigator: Navigator) {}

    @Composable
    override fun onRender(state: OrderScreenUiState, listener: OrderHistoryScreenInteractionListener) {
        LoginRequiredPlaceholder({})
/*        Column(
            modifier = Modifier.fillMaxSize().background(Theme.colors.background),
        ) {
            Text(
                modifier = Modifier.padding(top = 56.dp, bottom = 16.dp, start = 16.dp),
                text = Resources.strings.history,
                style = Theme.typography.headline,
                color = Theme.colors.contentPrimary
            )
            BpAnimatedTabLayout(
                tabItems = OrderScreenUiState.OrderSelectType.values().toList(),
                selectedTab = state.selectedType,
                onTabSelected = { listener.onClickTab(it) },
                modifier = Modifier.height(40.dp).fillMaxWidth().padding(horizontal = 16.dp),
                horizontalPadding = 4.dp,
            ) { type ->
                Text(
                    text = type.name.capitalizeFirstLetter(),
                    style = Theme.typography.titleMedium,
                    color = animateColorAsState(
                        if (type == state.selectedType) Theme.colors.onPrimary
                        else Theme.colors.contentTertiary
                    ).value,
                    modifier = Modifier.padding(4.dp)
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 24.dp)
            ) {
                when (state.selectedType) {
                    OrderScreenUiState.OrderSelectType.MEALS -> {
                        items(state.ordersHistory) {
                            MealOrderItem(orders = it)
                            HorizontalDivider()
                        }
                    }

                    OrderScreenUiState.OrderSelectType.TRIPS -> {
                        items(state.tripsHistory) {
                            TripHistoryItem(it)
                            HorizontalDivider()
                        }
                    }
                }
            }
        }*/
    }
}