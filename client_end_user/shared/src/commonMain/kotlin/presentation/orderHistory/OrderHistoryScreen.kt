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
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAnimatedTabLayout
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.base.BaseScreen
import presentation.orderHistory.composable.HorizontalDivider
import presentation.orderHistory.composable.MealOrderItem
import presentation.orderHistory.composable.TripHistoryItem
import util.capitalizeFirstLetter

class OrderHistoryScreen :
    BaseScreen<OrderHistoryScreenModel, OrderScreenUiState, OrderHistoryScreenUiEffect, OrderHistoryScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: OrderHistoryScreenUiEffect, navigator: Navigator) {}

    @Composable
    override fun onRender(state: OrderScreenUiState, listener: OrderHistoryScreenInteractionListener) {
        Column(
            modifier = Modifier.fillMaxSize().background(Theme.colors.background),
        ) {
            Text(
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp, start = 16.dp),
                text = "History",
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
        }

    }

}