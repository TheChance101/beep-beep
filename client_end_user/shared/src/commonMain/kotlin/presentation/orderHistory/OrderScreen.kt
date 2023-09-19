package presentation.orderHistory

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.koin.core.parameter.ParametersDefinition
import presentation.base.BaseScreen

class OrderScreen :
    BaseScreen<OrderScreenModel, OrderScreenUiState, OrderScreenUiEffect, OrderScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: OrderScreenUiEffect, navigator: Navigator) {

    }

    @Composable
    override fun onRender(state: OrderScreenUiState, listener: OrderScreenInteractionListener) {

    }

}