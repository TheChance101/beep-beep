package presentation.order

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composables.BPSnackBar
import presentation.login.LoginScreen
import presentation.order.composables.OrderCard
import presentation.order.composables.OrderTextButton
import resources.Resources

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
            OrderScreenUiEffect.ApproveOrder -> {}
            OrderScreenUiEffect.CancelOrder -> {}
            OrderScreenUiEffect.FinishOrder -> {}
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
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
                TotalOrders(text = Resources.strings.totalOrders, totalOrders = state.totalOrders)
            }

            FlowRow(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(Theme.dimens.space16),
                maxItemsInEachRow = 2,
                horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8),
            ) {

                ActiveOrders(
                    title = Resources.strings.inCookingOrders,
                    inCookingOrders = state.inCookingOrders,
                    onClickFinish = listener::onClickFinishOrder
                )

                ActiveOrders(
                    modifier = Modifier.padding(0.dp),
                    title = Resources.strings.requestedOrders,
                    pendingOrders = state.pendingOrders,
                    onClickCancel = listener::onClickCancelOrder,
                    onClickApprove = listener::onClickApproveOrder
                )

            }
        }
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
                Theme.dimens.space4,
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

    @Composable
    private fun ActiveOrders(
        title: String,
        modifier: Modifier = Modifier,
        inCookingOrders: List<OrderUiState>? = null,
        pendingOrders: List<OrderUiState>? = null,
        onClickFinish: () -> Unit = {},
        onClickCancel: () -> Unit = {},
        onClickApprove: () -> Unit = {},
        verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(Theme.dimens.space8)
    ) {
        Column(
            modifier = modifier
                .widthIn(max = 360.dp)
                .padding(bottom = Theme.dimens.space16),
            verticalArrangement = verticalArrangement,
        ) {
            Text(
                modifier = Modifier.padding(vertical = Theme.dimens.space8),
                text = title,
                style = Theme.typography.titleLarge,
                color = Theme.colors.contentPrimary
            )

            inCookingOrders?.let {
                it.forEach { order ->
                    OrderCard(order = order) {
                        OrderTextButton(text = Resources.strings.finish, onClick = onClickFinish)
                    }
                }
            }

            pendingOrders?.let {
                it.forEach { order ->
                    OrderCard(order = order) {
                        Row(horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8)) {
                            OrderTextButton(
                                text = Resources.strings.cancel,
                                onClick = onClickCancel,
                                textColor = Theme.colors.contentTertiary,
                                border = BorderStroke(0.dp, color = Theme.colors.surface)
                            )
                            OrderTextButton(
                                text = Resources.strings.approve,
                                onClick = onClickApprove,
                            )
                        }
                    }
                }
            }
        }
    }
}