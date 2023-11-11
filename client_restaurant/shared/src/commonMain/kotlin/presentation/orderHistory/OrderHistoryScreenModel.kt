package presentation.orderHistory

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Order
import domain.entity.OrderStatus
import domain.usecase.IManageOrderUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState


class OrderHistoryScreenModel(
    private val restaurantId: String, private val ordersManagement: IManageOrderUseCase
) : BaseScreenModel<OrderHistoryScreenUiState, OrderHistoryScreenUiEffect>(OrderHistoryScreenUiState()),
    OrderHistoryScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getData()
    }

    private fun getData() {
        tryToExecute(
            {
                ordersManagement.getOrdersHistory(restaurantId, 1, 10)
            },
            ::onOrdersSuccess,
            ::onError
        )
    }
    private fun onOrdersSuccess(orders: List<Order>) {
        val ordersUiState=orders .map { it.toOrderHistoryUiState() }
        updateState { currentState ->
            val finishedOrders = ordersUiState.filter { it.orderState == OrderStatus.DONE }
            val canceledOrders = ordersUiState.filter { it.orderState == OrderStatus.CANCELED }
            println("finishedOrders: $finishedOrders")
            println("canceledOrders: $canceledOrders")
            currentState.copy(
                finishedOrders = finishedOrders.takeIf { it.isNotEmpty() } ?: emptyList(),
                canceledOrders = canceledOrders.takeIf { it.isNotEmpty() } ?: emptyList()
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


