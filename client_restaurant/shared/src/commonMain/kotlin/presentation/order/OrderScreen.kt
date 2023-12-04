package presentation.order

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BPSnackBar
import com.beepbeep.designSystem.ui.composable.BpAnimationContent
import com.beepbeep.designSystem.ui.composable.modifier.shimmerEffect
import com.beepbeep.designSystem.ui.theme.Theme
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import presentation.base.BaseScreen
import presentation.composable.BpAppBar
import presentation.composable.LoadingOrderItem
import presentation.composable.NoItemsPlaceholder
import presentation.order.composable.OrderCard
import presentation.order.composable.OrderTextButton
import presentation.order.composable.header
import resources.Resources
import util.getNavigationBarPadding

class OrderScreen(private val restaurantId: String) :
    BaseScreen<OrderScreenModel, OrderScreenUiState, OrderScreenUiEffect, OrderScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel { parametersOf(restaurantId) })
    }

    override fun onEffect(effect: OrderScreenUiEffect, navigator: Navigator) {
        when (effect) {
            is OrderScreenUiEffect.Back -> navigator.pop()
            is OrderScreenUiEffect.UpdateOrder -> {}
            OrderScreenUiEffect.ShowUnknownError -> {}
            else -> {}
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: OrderScreenUiState, listener: OrderScreenInteractionListener) {
        Box(
            modifier = Modifier.fillMaxSize().background(Theme.colors.background)
                .padding(bottom = getNavigationBarPadding().calculateBottomPadding()),
            contentAlignment = Alignment.Center
        ) {
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

                BpAnimationContent(
                    state.isLoading,
                    content = {
                        BpAnimationContent(
                            state =(state.pendingOrders.isEmpty() && state.inCookingOrders.isEmpty() && !state.isLoading),
                            content = { OrderContent(state, listener) },
                            loadingContent = {
                                NoItemsPlaceholder(
                                    painter = painterResource(Resources.images.emptyScreen),
                                    text = Resources.strings.noOrderYet,
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                )
                            }
                        )
                    },
                    loadingContent = { OrderLoading() }
                )
            }
            BPSnackBar(
                icon = painterResource(Resources.images.bpIcon),
                iconBackgroundColor = Theme.colors.warningContainer,
                iconTint = Theme.colors.warning,
                isVisible = state.noInternetConnection,
                modifier = Modifier.padding(bottom = getNavigationBarPadding().calculateBottomPadding())
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = Resources.strings.noInternetConnection,
                    style = Theme.typography.body.copy(color = Theme.colors.contentPrimary),
                )
            }
            LaunchedEffect(state.noInternetConnection) {
                delay(1500)
                listener.onSnackBarDismissed()
            }
        }
    }

    @Composable
    fun OrderContent(
        state: OrderScreenUiState,
        listener: OrderScreenInteractionListener
    ) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp),
            columns = StaggeredGridCells.Adaptive(minSize = 360.dp),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (state.inCookingOrders.isNotEmpty()) {
                header {
                    OrdersHeader(
                        text = Resources.strings.inCookingOrders,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }


                items(state.inCookingOrders) { order ->
                    OrderCard(order = order) {
                        OrderTextButton(
                            text = Resources.strings.finish,
                            onClick = {
                                listener.onClickFinishOrder(order.orderId)
                            }
                        )
                    }
                }
            }

            if (state.pendingOrders.isNotEmpty()) {
                header {
                    OrdersHeader(
                        text = Resources.strings.requestedOrders,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                items(state.pendingOrders) { order ->
                    OrderCard(order = order) {
                        Row(horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8)) {
                            OrderTextButton(
                                text = Resources.strings.cancel,
                                onClick = {
                                    listener.onClickCancelOrder(order.orderId)
                                },
                                textColor = Theme.colors.contentTertiary,
                                border = BorderStroke(0.dp, color = Theme.colors.surface)
                            )
                            OrderTextButton(
                                text = Resources.strings.approve,
                                onClick = {
                                    listener.onClickApproveOrder(order.orderId)
                                },
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun OrderLoading() {
        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp),
            columns = StaggeredGridCells.Adaptive(minSize = 360.dp),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            header { Box {
                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .width(150.dp)
                            .height(18.dp)
                            .clip(RoundedCornerShape(Theme.radius.medium))
                            .shimmerEffect()
                    )
                } }
            items(3) { LoadingOrderItem() }
            header {
                Box {
                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .width(150.dp)
                            .height(18.dp)
                            .clip(RoundedCornerShape(Theme.radius.medium))
                            .shimmerEffect()
                    )
                }
            }
            items(3) { LoadingOrderItem() }
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
