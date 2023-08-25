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
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAnimatedTabLayout
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.base.BaseScreen
import presentation.composable.BpAppBar
import presentation.order.composable.OrderCard
import presentation.order.composable.header
import resources.Resources
import util.capitalizeFirstLetter

class OrdersHistoryScreen(private val restaurantId: String) : BaseScreen<OrderHistoryScreenModel, OrderHistoryScreenUiState,
        OrderHistoryScreenUiEffect, OrderHistoryScreenInteractionListener>() {

    @Composable
    override fun Content() {
        val screenModel: OrderHistoryScreenModel = rememberScreenModel { OrderHistoryScreenModel(restaurantId) }
        initScreen(screenModel)
    }

    @OptIn(ExperimentalFoundationApi::class)
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
                content = {}
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(300.dp),
                contentPadding = PaddingValues(Theme.dimens.space16),
                verticalItemSpacing = Theme.dimens.space8,
                horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8),
                modifier = Modifier.fillMaxSize()
            ) {
                header {
                    BpAnimatedTabLayout(
                        tabItems = OrderHistoryScreenUiState.OrderSelectType.values().toList(),
                        selectedTab = state.selectedType,
                        onTabSelected = { listener.onClickTab(it) },
                        modifier = Modifier.height(40.dp).fillMaxWidth(),
                        horizontalPadding =  Theme.dimens.space4,
                    ) { type ->
                        Text(
                            text = type.name.capitalizeFirstLetter(),
                            style = Theme.typography.titleMedium,
                            color = animateColorAsState(
                                if (type == state.selectedType) Theme.colors.onPrimary
                                else Theme.colors.contentTertiary
                            ).value,
                            modifier = Modifier.padding(Theme.dimens.space4)
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
                    Text(it.createdAt)
                }
            }
        }
    }

    override fun onEffect(effect: OrderHistoryScreenUiEffect, navigator: Navigator) {
        when (effect) {
            is OrderHistoryScreenUiEffect.Back -> navigator.pop()
            else -> {}
        }
    }
}
