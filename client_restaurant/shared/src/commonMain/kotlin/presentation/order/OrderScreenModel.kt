package presentation.order

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.OrderState
import domain.usecase.IManageOrderUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class OrderScreenModel(private val manageOrders: IManageOrderUseCase) :
    BaseScreenModel<OrderScreenUiState, OrderScreenUiEffect>(OrderScreenUiState()),
    OrderScreenInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope

    override fun onClickBack() {
        sendNewEffect(OrderScreenUiEffect.Back)
    }

    override fun onClickFinishOrder(orderId: String, orderState: OrderState) {
        updateOrderState(orderId, orderState)
    }

    override fun onClickCancelOrder(orderId: String, orderState: OrderState) {
        updateOrderState(orderId, orderState)
    }

    override fun onClickApproveOrder(orderId: String, orderState: OrderState) {
        updateOrderState(orderId, orderState)
    }

    init {
        getInCookingOrders()
        getPendingOrders()
    }

    private fun updateOrderState(orderId: String, orderState: OrderState) {
        tryToExecute(
            { manageOrders.updateOrderState(orderId, orderState).toOrderUiState() },
            ::onUpdateOrderStateSuccess,
            ::onUpdateOrderStateError
        )
    }

    private fun getPendingOrders() {
        tryToExecute(
            {
                manageOrders.getCurrentOrders()
                    .filter { it.orderState == OrderState.PENDING }
                    .map { it.toOrderUiState() }
            },
            ::onGetPendingOrdersSuccess,
            ::onGetOrdersError
        )
    }

    private fun getInCookingOrders() {
        tryToExecute(
            {
                manageOrders.getCurrentOrders()
                    .filter { it.orderState == OrderState.IN_COOKING }
                    .map { it.toOrderUiState() }
            },
            ::onGetCookingSuccess,
            ::onGetOrdersError
        )
    }

    private fun onGetPendingOrdersSuccess(pendingOrders: List<OrderUiState>) {
        val totalOrders = state.value.totalOrders
        val newTotalOrders = totalOrders + pendingOrders.size

        updateState { it.copy(pendingOrders = pendingOrders, totalOrders = newTotalOrders) }
    }

    private fun onGetCookingSuccess(inCookingOrders: List<OrderUiState>) {
        val totalOrders = state.value.totalOrders
        val newTotalOrders = totalOrders + inCookingOrders.size
        updateState { it.copy(inCookingOrders = inCookingOrders, totalOrders = newTotalOrders) }
    }

    private fun onUpdateOrderStateSuccess(updatedOrder: OrderUiState) {
        sendNewEffect(OrderScreenUiEffect.UpdateOrder(updatedOrder))
    }

    private fun onGetOrdersError(errorState: ErrorState) {
        println("Error is $errorState")
    }

    private fun onUpdateOrderStateError(errorState: ErrorState) {
        println("Error is $errorState")
    }
}