package presentation.order

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Order
import domain.entity.OrderStatus
import domain.usecase.IManageOrderUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class OrderScreenModel(
    private val restaurantId: String,
    private val manageOrders: IManageOrderUseCase
) : BaseScreenModel<OrderScreenUiState, OrderScreenUiEffect>(OrderScreenUiState()),
    OrderScreenInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getOrders()
        getPendingOrders()
    }

    override fun onClickBack() {
        sendNewEffect(OrderScreenUiEffect.Back)
    }

    override fun onClickFinishOrder(orderId: String) {
        updateOrderState(orderId)
    }

    override fun onClickCancelOrder(orderId: String) {
        updateOrderState(orderId)
    }

    override fun onClickApproveOrder(orderId: String) {
        updateOrderState(orderId)
    }

    private fun updateOrderState(orderId: String) {
        tryToExecute(
            { manageOrders.updateOrderState(orderId).toOrderUiState() },
            ::onUpdateOrderStateSuccess,
            ::onUpdateOrderStateError
        )
    }

    private fun getPendingOrders() {
        println("getPendingOrders")
        tryToCollect(
            { manageOrders.getCurrentOrders(restaurantId) },
            ::onGetPendingOrdersSuccess,
            ::onGetOrdersError
        )
    }

    private fun onGetPendingOrdersSuccess(orders: Order) {
        val pendingOrders = orders.orderState == OrderStatus.PENDING
        if (pendingOrders) {
            val totalOrders = state.value.totalOrders
            val newTotalOrders = totalOrders.plus(1)
            updateState {
                it.copy(
                    pendingOrders = state.value.pendingOrders + orders.toOrderUiState(),
                    totalOrders = newTotalOrders
                )
            }
        }
    }

    private fun getOrders() {
        tryToExecute(
            { manageOrders.getActiveOrders(restaurantId) },
            ::onGetOrdersSuccess,
            ::onGetOrdersError
        )
    }

    private fun onGetOrdersSuccess(orders: List<Order>) {
        val ordersUiState = orders.map { it.toOrderUiState() }
        updateState { currentState ->
            currentState.copy(
                inCookingOrders = ordersUiState.filter { it.orderState == OrderStatus.IN_COOKING },
                pendingOrders = ordersUiState.filter { it.orderState == OrderStatus.PENDING },
                totalOrders = state.value.inCookingOrders.size + state.value.pendingOrders.size
            )
        }
    }

    private fun onUpdateOrderStateSuccess(updatedOrder: OrderUiState) {
        sendNewEffect(OrderScreenUiEffect.UpdateOrder(updatedOrder))
    }

    private fun onGetOrdersError(errorState: ErrorState) {
        println("onGetOrdersError is $errorState")
    }

    private fun onUpdateOrderStateError(errorState: ErrorState) {
        println("Error is $errorState")
    }
}