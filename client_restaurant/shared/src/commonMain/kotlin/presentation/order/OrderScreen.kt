package presentation.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.base.BaseScreen
import presentation.composables.OrderCard
import presentation.composables.header
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

    @Composable
    override fun onRender(state: OrderScreenUiState, listener: OrderScreenInteractionListener) {

        Column(modifier = Modifier.background(Theme.colors.background)) {

            LazyVerticalGrid(
                contentPadding = PaddingValues(Theme.dimens.space16),
                columns = GridCells.Adaptive(minSize = 328.dp),
                verticalArrangement = Arrangement.spacedBy(Theme.dimens.space8),
                horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8)
            ) {

                header {
                    Text(
                        modifier = Modifier.padding(vertical = Theme.dimens.space8),
                        text = Resources.strings.requestedOrders,
                        style = Theme.typography.titleLarge,
                        color = Theme.colors.contentPrimary
                    )
                }
                items(state.orders) { order ->
                    OrderCard(orders = order) {
                        BpOutlinedButton(title = state.)
                    }
                }

            }

        }
    }
}