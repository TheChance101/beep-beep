package presentation.order.orderHistory

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Order
import domain.usecase.IManageOrderUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState


class OrderHistoryScreenModel(
    private val restaurantId: String,
    private val ordersManagement: IManageOrderUseCase
) : BaseScreenModel<OrderHistoryScreenUiState, OrderHistoryScreenUiEffect>(OrderHistoryScreenUiState()),
    OrderHistoryScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getData()
    }

    private fun getData() {
        tryToExecute(
            ::getSelectedOrders,
            ::onOrdersSuccess,
            ::onError
        )
    }

    private suspend fun getSelectedOrders(): List<Order> {
        return when (state.value.selectedType) {
            OrderHistoryScreenUiState.OrderSelectType.FINISHED -> {
                ordersManagement.getFinishedOrdersHistory(restaurantId)
            }

            OrderHistoryScreenUiState.OrderSelectType.CANCELLED -> {
                ordersManagement.getCanceledOrdersHistory(restaurantId)
            }
        }
    }

    private fun onOrdersSuccess(orders: List<Order>) {
        updateState {
            it.copy(
                errorState = null,
                orders = orders.map { order -> order.toOrderHistoryUiState() }
            )
        }
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(errorState = errorState) }
    }

    override fun onClickBack() {
        sendNewEffect(OrderHistoryScreenUiEffect.Back)
    }

    override fun onClickTab(type: OrderHistoryScreenUiState.OrderSelectType) {
        updateState { it.copy(selectedType = type) }
        getData()
    }
}


