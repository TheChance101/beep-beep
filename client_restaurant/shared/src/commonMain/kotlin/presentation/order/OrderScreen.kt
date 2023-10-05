package presentation.order

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import domain.entity.OrderState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composable.BpAppBar
import presentation.composable.BpEmptyScreen
import presentation.order.composable.OrderCard
import presentation.order.composable.OrderTextButton
import presentation.order.composable.header
import resources.Resources

class OrderScreen :
    BaseScreen<OrderScreenModel, OrderScreenUiState, OrderScreenUiEffect, OrderScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: OrderScreenUiEffect, navigator: Navigator) {
        when (effect) {
            is OrderScreenUiEffect.Back -> navigator.pop()
            is OrderScreenUiEffect.UpdateOrder -> {}
        }
    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: OrderScreenUiState, listener: OrderScreenInteractionListener) {

        Column(modifier = Modifier.fillMaxSize().background(Theme.colors.background)) {
            BpAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colors.surface)
                    .border(width = 1.dp, color = Theme.colors.divider, shape = RectangleShape),
                onNavigateUp = listener::onClickBack,
                title = Resources.strings.orders
            ) {
                TotalOrders(
                    text = Resources.strings.totalOrders,
                    totalOrders = state.totalOrders,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            BpEmptyScreen(
                painter = painterResource(Resources.images.emptyScreen),
                text = Resources.strings.noOrderYet,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                isVisible = (state.pendingOrders.isEmpty()&&state.inCookingOrders.isEmpty())
            )
            LazyVerticalStaggeredGrid(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                columns = StaggeredGridCells.Adaptive(minSize = 360.dp),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                header {
                    OrdersHeader(
                        text = Resources.strings.inCookingOrders,
                        modifier = Modifier.padding(bottom = 8.dp)
                            .alpha(if (state.inCookingOrders.isEmpty()) 0f else 1f)
                    )
                }

                items(state.inCookingOrders) { order ->
                    OrderCard(order = order) {
                        OrderTextButton(
                            text = Resources.strings.finish,
                            onClick = {
                                listener.onClickFinishOrder(order.id, order.orderState)
                            }
                        )
                    }
                }

                header {
                    OrdersHeader(
                        text = Resources.strings.requestedOrders,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                            .alpha(if (state.pendingOrders.isEmpty()) 0f else 1f)
                    )
                }

                items(state.pendingOrders) { order ->
                    OrderCard(order = order) {
                        Row(horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8)) {
                            OrderTextButton(
                                text = Resources.strings.cancel,
                                onClick = {
                                    listener.onClickCancelOrder(order.id, OrderState.CANCELED)
                                },
                                textColor = Theme.colors.contentTertiary,
                                border = BorderStroke(0.dp, color = Theme.colors.surface)
                            )
                            OrderTextButton(
                                text = Resources.strings.approve,
                                onClick = {
                                    listener.onClickCancelOrder(order.id, order.orderState)
                                },
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun OrdersHeader(text: String, modifier: Modifier = Modifier) {
        Text(
            modifier = modifier,
            text = text,
            style = Theme.typography.titleLarge,
            color = Theme.colors.contentPrimary
        )
    }

    @Composable
    private fun TotalOrders(
        text: String,
        totalOrders: Int,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(
                4.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = text,
                style = Theme.typography.caption,
                color = Theme.colors.contentSecondary
            )
            Text(
                text = totalOrders.toString(),
                style = Theme.typography.titleLarge,
                color = Theme.colors.contentPrimary
            )
        }
    }
}
