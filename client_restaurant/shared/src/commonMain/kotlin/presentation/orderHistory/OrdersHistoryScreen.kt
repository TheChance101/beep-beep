package presentation.orderHistory

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.beepbeep.designSystem.ui.composable.modifier.shimmerEffect
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import presentation.base.BaseScreen
import presentation.composable.BpAppBar
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
            AnimatedContent(state.isLoading) {
                if (state.isLoading) {
                    LoadingOrdersHistory()
                } else {
                    OrdersHistoryContent(state, listener)
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalResourceApi::class)
    private fun OrdersHistoryContent(
        state: OrderHistoryScreenUiState,
        listener: OrderHistoryScreenInteractionListener
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
                    modifier = Modifier.height(49.dp).fillMaxWidth().padding(bottom = 8.dp),
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

        NoItemsPlaceholder(
            painter = painterResource(Resources.images.emptyScreen),
            text = Resources.strings.noOrderHistory,
            isVisible = (state.orders.isEmpty()),
        )
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
            items(13) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(70.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxHeight().width(100.dp)
                            .clip(shape = RoundedCornerShape(Theme.radius.medium))
                            .shimmerEffect()
                    )

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(
                            8.dp,
                            alignment = Alignment.CenterVertically
                        ),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp)
                                .padding(top = 8.dp, end = 16.dp)
                                .shimmerEffect()
                        )
                        Box(
                            modifier = Modifier
                                .width(90.dp)
                                .height(16.dp)
                                .padding(top = 8.dp)
                                .shimmerEffect()
                        )
                    }
                }
            }
        }
    }
}
