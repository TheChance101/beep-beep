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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAppBar
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.base.BaseScreen
import presentation.composables.OrderCard
import presentation.composables.OrderTextButton
import presentation.composables.header
import presentation.login.LoginScreen
import resources.Resources
import util.OrderState

class OrderScreen :
    BaseScreen<OrderScreenModel, OrderScreenUiState, OrderScreenUiEffect, OrderScreenInteractionListener>() {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { OrderScreenModel() }
        initScreen(screenModel)
    }

    override fun onEffect(effect: OrderScreenUiEffect, navigator: Navigator) {
        when (effect) {
            is OrderScreenUiEffect.Back -> navigator.push(LoginScreen())
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun onRender(state: OrderScreenUiState, listener: OrderScreenInteractionListener) {

        Column(modifier = Modifier.fillMaxSize().background(Theme.colors.background)) {

            BpAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colors.surface)
                    .border(width = 1.dp, color = Theme.colors.divider, shape = RectangleShape),
                onNavigateUp = {},
                title = Resources.strings.orders
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(
                        Theme.dimens.space4,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = Resources.strings.totalOrders,
                        style = Theme.typography.caption,
                        color = Theme.colors.contentSecondary
                    )
                    Text(
                        text = state.activeOrders.size.toString(),
                        style = Theme.typography.titleLarge,
                        color = Theme.colors.contentPrimary
                    )
                }
            }

            LazyVerticalStaggeredGrid(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Theme.dimens.space16),
                columns = StaggeredGridCells.Adaptive(minSize = 300.dp),
                verticalItemSpacing = Theme.dimens.space8,
                horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8)
            ) {

                header {
                    Text(
                        modifier = Modifier.padding(vertical = Theme.dimens.space8),
                        text = Resources.strings.inCookingOrders,
                        style = Theme.typography.titleLarge,
                        color = Theme.colors.contentPrimary
                    )
                }

                items(state.activeOrders.filter { it.orderState == OrderState.IN_COOKING.statusCode }) { order ->
                    OrderCard(orders = order) {
                        OrderTextButton(
                            text = Resources.strings.finish,
                            onClick = {},
                        )
                    }
                }

                header {
                    Text(
                        modifier = Modifier.padding(
                            top = Theme.dimens.space24,
                            bottom = Theme.dimens.space16
                        ),
                        text = Resources.strings.requestedOrders,
                        style = Theme.typography.titleLarge,
                        color = Theme.colors.contentPrimary
                    )
                }
                items(state.activeOrders.filter { it.orderState == OrderState.PENDING.statusCode }) { order ->
                    OrderCard(orders = order) {
                        Row(horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8)) {
                            OrderTextButton(
                                text = Resources.strings.cancel,
                                onClick = {},
                                textColor = Theme.colors.contentTertiary,
                                border = BorderStroke(0.dp, color = Theme.colors.surface)
                            )
                            OrderTextButton(
                                text = Resources.strings.approve,
                                onClick = {},
                            )
                        }
                    }
                }

            }

        }
    }
}