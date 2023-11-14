package presentation.order

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Order
import domain.entity.OrderStatus
import domain.usecase.IManageOrderUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class OrderScreenModel(
    private val restaurantId: String,
    private val manageOrders: IManageOrderUseCase
) : BaseScreenModel<OrderScreenUiState, OrderScreenUiEffect>(OrderScreenUiState()),
    OrderScreenInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope
    private var cancelOrderJob: Job? = null
    private var updateOrderJob: Job? = null



    init {
        getOrders()
        getPendingOrders()
    }

    override fun onClickBack() {
        sendNewEffect(OrderScreenUiEffect.Back)
    }

    override fun onClickCancelOrder(orderId: String) {
        updateState { it.copy(isLoading = true,noInternetConnection = false) }
        cancelOrderJob?.cancel()
        cancelOrderJob = launchDelayed(500L) {
            tryToExecute(
                { manageOrders.cancelOrder(orderId) },
                ::onCancelOrderSuccess,
                ::onError
            )
        }
    }

    private fun onCancelOrderSuccess(updatedOrder: Order) {
        updateState { it.copy(isLoading = false) }
        val order = state.value.pendingOrders.find { it.id == updatedOrder.id }
        updateState {
            it.copy(
                pendingOrders = state.value.pendingOrders.toMutableList().apply { remove(order) }
            )
        }
    }


    override fun onClickApproveOrder(orderId: String) {
        updateOrderState(orderId)
    }

    override fun onSnackBarDismissed() {
        updateState { it.copy(noInternetConnection = false) }
    }

    override fun onClickFinishOrder(orderId: String) {
        updateOrderState(orderId)
    }

    private fun updateOrderState(orderId: String) {
        updateState { it.copy(isLoading = true,noInternetConnection = false) }
        updateOrderJob?.cancel()
        updateOrderJob = launchDelayed(500L) {
        tryToExecute(
            { manageOrders.updateOrderState(orderId).toOrderUiState() },
            ::onUpdateOrderStateSuccess,
            ::onError
        )
        }
    }

    private fun onUpdateOrderStateSuccess(updatedOrder: OrderUiState) {
        updateState { it.copy(isLoading = false) }
        val inCookingOrders = state.value.inCookingOrders.toMutableList()
        val pendingOrders = state.value.pendingOrders.toMutableList()

        inCookingOrders.find { it.id == updatedOrder.id }?.let {
            if (it.orderState == OrderStatus.IN_COOKING) {
                inCookingOrders.remove(it)
                updateState {currentState-> currentState.copy(totalOrders =currentState.totalOrders.minus(1) ) }
            }
        }
        pendingOrders.find { it.id == updatedOrder.id }?.let {
            if (it.orderState == OrderStatus.PENDING) {
                updateOrderState(updatedOrder.id)
                pendingOrders.remove(it)
            }
        }
        if (updatedOrder.orderState == OrderStatus.IN_COOKING) {
            inCookingOrders.add(updatedOrder)
        }
        updateState { it.copy(inCookingOrders = inCookingOrders, pendingOrders = pendingOrders) }
    }

    private fun getPendingOrders() {
        updateState { it.copy(isLoading = true,noInternetConnection = false) }
        tryToCollect(
            { manageOrders.getCurrentOrders(restaurantId) },
            ::onGetPendingOrdersSuccess,
            ::onError
        )
    }

    private fun onGetPendingOrdersSuccess(orders: Order) {
        updateState { it.copy(
            pendingOrders = state.value.pendingOrders.toMutableList().apply {add(orders.toOrderUiState()) },
            totalOrders = state.value.totalOrders.plus(1),
            isLoading = false,
        )
        }
    }

    private fun getOrders() {
        updateState { it.copy(isLoading = true,noInternetConnection = false) }
        tryToExecute(
            { manageOrders.getActiveOrders(restaurantId) },
            ::onGetOrdersSuccess,
            ::onError
        )
    }

    private fun onGetOrdersSuccess(orders: List<Order>) {
        updateState { it.copy(isLoading = false) }
        val ordersUiState = orders.map { it.toOrderUiState() }
        val cookingOrders= ordersUiState.filter { it.orderState == OrderStatus.IN_COOKING }
        val pendingOrders = ordersUiState.filter { it.orderState == OrderStatus.PENDING }
        updateState { currentState ->
            currentState.copy(inCookingOrders = cookingOrders, pendingOrders = pendingOrders,
                totalOrders = cookingOrders.size + pendingOrders.size
            )
        }
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(isLoading = false) }
        println("onError is: $errorState")
        when (errorState) {
            is ErrorState.NoInternet -> {
                updateState { it.copy(noInternetConnection = true) }
            }
            else -> {
                sendNewEffect(OrderScreenUiEffect.ShowUnknownError)
            }
        }
    }

}