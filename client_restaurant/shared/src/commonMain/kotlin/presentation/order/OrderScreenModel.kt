package presentation.order

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageOrderUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.base.RequestException

class OrderScreenModel :
    BaseScreenModel<OrderScreenUiState, OrderScreenUiEffect>(OrderScreenUiState()),
    OrderScreenInteractionListener {
    private val manageOrdersUseCase: IManageOrderUseCase by inject()

    override val viewModelScope: CoroutineScope = coroutineScope

    override fun onClickBack() {
        sendNewEffect(OrderScreenUiEffect.Back)
    }

    override fun onClickFinishOrder(orderId: String, orderStateType: OrderStateType) {
        updateOrderState(orderId, orderStateType)
    }

    override fun onClickCancelOrder(orderId: String, orderStateType: OrderStateType) {
        updateOrderState(orderId, orderStateType)
    }

    override fun onClickApproveOrder(orderId: String, orderStateType: OrderStateType) {
        updateOrderState(orderId, orderStateType)
    }

    init {
        getInCookingOrders("7c3d631e-6d49-48c9-9f91-9426ec559eb1")
        getPendingOrders("7c3d631e-6d49-48c9-9f91-9426ec559eb1")
    }

    private fun updateOrderState(orderId: String, orderState: OrderStateType) {
        tryToExecute(
            { manageOrdersUseCase.updateOrderState(orderId, orderState) },
            { onUpdateOrderStateSuccess(it, orderState) },
            ::onUpdateOrderStateError
        )
    }


    private fun onUpdateOrderStateSuccess(isOrderUpdated: Boolean, orderState: OrderStateType) {
        if (orderState == OrderStateType.FINISH && isOrderUpdated) {
            sendNewEffect(OrderScreenUiEffect.FinishOrder)
        } else if (orderState == OrderStateType.APPROVE && isOrderUpdated) {
            sendNewEffect(OrderScreenUiEffect.ApproveOrder)
        } else if (orderState == OrderStateType.CANCEL && isOrderUpdated) {
            sendNewEffect(OrderScreenUiEffect.CancelOrder)
        } else {
            throw RequestException()
        }
    }

    private fun getPendingOrders(restaurantId: String) {
        tryToExecute(
            { manageOrdersUseCase.getPendingOrders(restaurantId).map { it.toOrderUiState() } },
            ::onGetPendingOrdersSuccess,
            ::onGetOrdersError
        )
    }

    private fun getInCookingOrders(restaurantId: String) {
        tryToExecute(
            { manageOrdersUseCase.getInCookingOrders(restaurantId).map { it.toOrderUiState() } },
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

    private fun onGetOrdersError(errorState: ErrorState) {
        println("Error is $errorState")
    }

    private fun onUpdateOrderStateError(errorState: ErrorState) {
        println("Error is $errorState")
    }

}