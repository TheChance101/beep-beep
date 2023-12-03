package presentation.orderHistory

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAnimatedTabLayout
import com.beepbeep.designSystem.ui.composable.BpAnimationContent
import com.beepbeep.designSystem.ui.composable.modifier.shimmerEffect
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import presentation.base.BaseScreen
import presentation.composable.BpAppBar
import presentation.composable.LoadingOrderItem
import presentation.composable.NoItemsPlaceholder
import presentation.order.composable.OrderCard
import presentation.order.composable.header
import resources.Resources
import util.capitalizeFirstLetter
import util.getNavigationBarPadding

class OrdersHistoryScreen(private val restaurantId: String) :
    BaseScreen<OrderHistoryScreenModel, OrderHistoryScreenUiState,
            OrderHistoryScreenUiEffect, OrderHistoryScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel { parametersOf(restaurantId) })
    }

    override fun onEffect(effect: OrderHistoryScreenUiEffect, navigator: Navigator) {
        when (effect) {
            is OrderHistoryScreenUiEffect.Back -> navigator.pop()
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(
        state: OrderHistoryScreenUiState,
        listener: OrderHistoryScreenInteractionListener,
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
            BpAnimationContent(
                state.isLoading,
                content = {
                    BpAnimationContent(
                        state = state.orders.isEmpty(),
                        content = { OrdersHistoryContent(state, listener) },
                        loadingContent = {
                            NoItemsPlaceholder(
                                painter = painterResource(Resources.images.emptyScreen),
                                text = Resources.strings.noOrderHistory,
                            )
                        }
                    )
                },
                loadingContent = { LoadingOrdersHistory() }
            )
        }
    }

    @Composable
    private fun OrdersHistoryContent(
        state: OrderHistoryScreenUiState,
        listener: OrderHistoryScreenInteractionListener,
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(300.dp),
            contentPadding = PaddingValues(16.dp),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
                .padding(bottom = getNavigationBarPadding().calculateBottomPadding())
        ) {
            header {
                BpAnimatedTabLayout(
                    tabItems = OrderHistoryScreenUiState.OrderSelectType.values().toList(),
                    selectedTab = state.selectedType,
                    onTabSelected = { listener.onClickTab(it) },
                    modifier = Modifier.height(54.dp).fillMaxWidth().padding(bottom = 8.dp),
                    horizontalPadding = 4.dp,
                ) { type ->
                    Text(
                        text = type.name.capitalizeFirstLetter(),
                        style = Theme.typography.titleMedium,
                        color = animateColorAsState(
                            if (type == state.selectedType) Theme.colors.onPrimary
                            else Theme.colors.contentTertiary
                        ).value,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            items(state.currentOrders, key = { it.orderId }) { order ->
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
    }

    @Composable
    fun LoadingOrdersHistory() {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(300.dp),
            contentPadding = PaddingValues(16.dp),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
                .padding(bottom = getNavigationBarPadding().calculateBottomPadding())
        ) {
            header {
                Box {
                    Row(
                        Modifier.fillMaxWidth().clip(RoundedCornerShape(Theme.radius.medium))
                            .background(Theme.colors.surface).wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier.height(42.dp).padding(4.dp).weight(1f)
                                .clip(RoundedCornerShape(Theme.radius.medium))
                                .shimmerEffect()
                        )
                        Box(
                            modifier = Modifier.weight(1f)
                                .clip(RoundedCornerShape(Theme.radius.medium))
                        )
                    }
                }
            }
            items(5) {
                LoadingOrderItem()
            }
        }
    }
}
