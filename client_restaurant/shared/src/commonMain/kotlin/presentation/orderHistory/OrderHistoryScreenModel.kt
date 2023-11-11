package presentation.orderHistory

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.OrderStatus
import domain.usecase.IManageOrderUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.order.OrderUiState


class OrderHistoryScreenModel(
    private val restaurantId: String, private val ordersManagement: IManageOrderUseCase
) : BaseScreenModel<OrderHistoryScreenUiState, OrderHistoryScreenUiEffect>(OrderHistoryScreenUiState()),
    OrderHistoryScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getData()
    }

    private fun getData() {
        println("getData")
        tryToExecute(
            {
                ordersManagement.getOrdersHistory(restaurantId, 1, 10)
                    .map { it.toOrderHistoryUiState() }
            },
            ::onOrdersSuccess,
            ::onError
        )
    }

    //    private suspend fun getSelectedOrders(): List<Order> {
//
//      return  ordersManagement.getOrdersHistory(restaurantId, 1, 10)
//
////        return when (state.value.selectedType) {
////
////            OrderHistoryScreenUiState.OrderSelectType.FINISHED -> {
////                ordersManagement.getOrdersHistory(restaurantId,1,10)
////            }
////
////            OrderHistoryScreenUiState.OrderSelectType.CANCELLED -> {
////            }
////        }
//    }
    private fun onOrdersSuccess(orders: List<OrderUiState>) {
        updateState { currentState ->
            val finishedOrders = orders.filter { it.orderState == OrderStatus.DONE.key }
            val canceledOrders = orders.filter { it.orderState == OrderStatus.CANCELED.key }
            println("finishedOrders: $finishedOrders")
            println("canceledOrders: $canceledOrders")
            currentState.copy(
                finishedOrders = finishedOrders.takeIf { it.isNotEmpty() } ?: emptyList(),
                canceledOrders = canceledOrders.takeIf { it.isNotEmpty() } ?: emptyList()
            )
        }
    }

    private fun onError(errorState: ErrorState) {
        println("errorState: $errorState")
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


