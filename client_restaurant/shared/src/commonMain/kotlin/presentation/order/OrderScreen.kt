package presentation.order

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import presentation.base.BaseScreen
import presentation.composables.OrderCard
import presentation.login.LoginScreen

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
//        println("LLLLLL : ${state.orders}")
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 328.dp)) {

            items(state.orders) { order ->
                OrderCard(
                    items = order.orderMealUiStates,
                    totalPrice = order.totalPrice
                ) {
                }
            }

        }


    }


}