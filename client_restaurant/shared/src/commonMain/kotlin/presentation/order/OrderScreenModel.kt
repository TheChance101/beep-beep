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
        getInCookingOrders()
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
            updateState { it.copy(pendingOrders = state.value.pendingOrders + orders.toOrderUiState(), totalOrders = newTotalOrders) }
        }
    }

    private fun getInCookingOrders() {
        tryToCollect(
            {manageOrders.getCurrentOrders(restaurantId)},
            ::onGetCookingSuccess,
            ::onGetOrdersError
        )
    }

    private fun onGetCookingSuccess(orders: Order) {
        val inCookingOrders = orders.orderState == OrderStatus.IN_COOKING
        if (inCookingOrders) {
            val totalOrders = state.value.totalOrders
            val newTotalOrders = totalOrders.plus(1)
            updateState { it.copy(inCookingOrders = state.value.inCookingOrders + orders.toOrderUiState(), totalOrders = newTotalOrders) }
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