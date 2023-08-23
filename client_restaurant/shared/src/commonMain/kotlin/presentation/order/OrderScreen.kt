package presentation.order

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import presentation.base.BaseScreen
import presentation.composables.OrderCard
import presentation.composables.OrderTextButton
import presentation.login.LoginScreen
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
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun onRender(state: OrderScreenUiState, listener: OrderScreenInteractionListener) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background)
        ) {

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
                        text = state.totalOrders.toString(),
                        style = Theme.typography.titleLarge,
                        color = Theme.colors.contentPrimary
                    )
                }
            }

            FlowRow(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(Theme.dimens.space16),
                maxItemsInEachRow = 2,
                horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8),
            ) {

                Column(
                    modifier = Modifier.widthIn(max = 360.dp)
                        .padding(bottom = Theme.dimens.space16),
                    verticalArrangement = Arrangement.spacedBy(Theme.dimens.space8),
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = Theme.dimens.space8),
                        text = Resources.strings.inCookingOrders,
                        style = Theme.typography.titleLarge,
                        color = Theme.colors.contentPrimary
                    )

                    state.inCookingOrders.forEach { order ->
                        OrderCard(order = order) {
                            OrderTextButton(
                                text = Resources.strings.finish,
                                onClick = {},
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier.widthIn(max = 360.dp),
                    verticalArrangement = Arrangement.spacedBy(Theme.dimens.space8)

                ) {
                    Text(
                        modifier = Modifier.padding(vertical = Theme.dimens.space8),
                        text = Resources.strings.requestedOrders,
                        style = Theme.typography.titleLarge,
                        color = Theme.colors.contentPrimary
                    )

                    state.pendingOrders.forEach { order ->
                        OrderCard(order = order) {
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
