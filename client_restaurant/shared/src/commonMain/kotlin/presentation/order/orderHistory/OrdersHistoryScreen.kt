package presentation.order.orderHistory

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAnimatedTabLayout
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import presentation.base.BaseScreen
import presentation.composable.BpAppBar
import presentation.composable.BpEmptyScreen
import presentation.order.composable.OrderCard
import presentation.order.composable.header
import resources.Resources
import util.capitalizeFirstLetter

class OrdersHistoryScreen(private val restaurantId: String) :
    BaseScreen<OrderHistoryScreenModel, OrderHistoryScreenUiState,
            OrderHistoryScreenUiEffect, OrderHistoryScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel { parametersOf(restaurantId) })
    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
    @Composable
    override fun onRender(
        state: OrderHistoryScreenUiState,
        listener: OrderHistoryScreenInteractionListener
    ) {
        Column(Modifier.fillMaxSize().background(Theme.colors.background)) {
            BpAppBar(
                onNavigateUp = { listener.onClickBack() },
                title = Resources.strings.orderHistory,
                modifier = Modifier.fillMaxWidth()
                    .background(Theme.colors.surface)
                    .border(width = 1.dp, color = Theme.colors.divider, shape = RectangleShape),
                actions = {}
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(300.dp),
                contentPadding = PaddingValues(16.dp),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                header {
                    BpAnimatedTabLayout(
                        tabItems = OrderHistoryScreenUiState.OrderSelectType.values().toList(),
                        selectedTab = state.selectedType,
                        onTabSelected = { listener.onClickTab(it) },
                        modifier = Modifier.height(40.dp).fillMaxWidth(),
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
                }
                items(state.orders, key = { it.id }) { order ->
                    OrderCard(
                        order = order,
                        modifier = Modifier
                    ) {
                        Text(
                            order.createdAt,
                            style = Theme.typography.caption.copy(color = Theme.colors.contentTertiary),
                        )
                    }
                }
            }
            state.orders.forEach {
                OrderCard(
                    order = it,
                    modifier = Modifier
                ) {
                    Text(
                        it.createdAt,
                        style = Theme.typography.caption.copy(color = Theme.colors.contentTertiary)
                    )
                }
            }
        }
        BpEmptyScreen(
            painter = painterResource(Resources.images.emptyScreen),
            text = Resources.strings.noOrderHistory,
            isVisible = (state.orders.isEmpty()),
        )
    }

    override fun onEffect(effect: OrderHistoryScreenUiEffect, navigator: Navigator) {
        when (effect) {
            is OrderHistoryScreenUiEffect.Back -> navigator.pop()
            else -> {}
        }
    }
}
