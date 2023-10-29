package presentation.orderHistory

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAnimatedTabLayout
import com.beepbeep.designSystem.ui.composable.BpPagingList
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.auth.login.LoginScreen
import presentation.base.BaseScreen
import presentation.composable.ContentVisibility
import presentation.composable.LoginRequiredPlaceholder
import presentation.composable.HorizontalDivider
import presentation.orderHistory.composable.MealOrderItem
import presentation.orderHistory.composable.TripHistoryItem
import resources.Resources
import util.capitalizeFirstLetter
import util.root

class OrderHistoryScreen :
    BaseScreen<OrderHistoryScreenModel, OrderScreenUiState, OrderHistoryScreenUiEffect, OrderHistoryScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: OrderHistoryScreenUiEffect, navigator: Navigator) {
        when (effect) {
            OrderHistoryScreenUiEffect.NavigateToLoginScreen -> navigator.root?.push(LoginScreen())
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(
        state: OrderScreenUiState,
        listener: OrderHistoryScreenInteractionListener,
    ) {
        val foodOrders = state.ordersHistory.collectAsLazyPagingItems()
        val trips = state.tripsHistory.collectAsLazyPagingItems()

        LoginRequiredPlaceholder(
            placeHolder = painterResource(Resources.images.requireLoginToShowOrdersHistoryPlaceholder),
            message = Resources.strings.ordersHistoryLoginMessage,
            onClickLogin = listener::onClickLogin
        )

        ContentVisibility(state.isLoggedIn) {
            Column(modifier = Modifier.fillMaxSize().background(Theme.colors.background)) {
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
                when (state.selectedType) {
                    OrderScreenUiState.OrderSelectType.MEALS -> {
                        BpPagingList(
                            data = foodOrders,
                        ) { foodOrder ->
                            foodOrder?.let {
                                MealOrderItem(orders = foodOrder)
                                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }

                    OrderScreenUiState.OrderSelectType.TRIPS -> {

                        BpPagingList(
                            data = trips,
                        ) { trip ->
                            trip?.let {
                                TripHistoryItem(it)
                                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }
                }
            }
        }
    }
}
