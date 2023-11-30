package presentation.orderHistory

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Order
import domain.entity.OrderStatus
import domain.usecase.IManageOrderUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { ordersManagement.getOrdersHistory(restaurantId) },
            ::onGetOrdersHistorySuccess,
            ::onError
        )
    }

    private fun onGetOrdersHistorySuccess(orders: Flow<PagingData<Order>>) {
        val ordersUiState = orders.map { pagingData ->
            pagingData.map { order -> order.toOrderHistoryUiState() }
        }

        updateState { currentState ->
            currentState.copy(
                isLoading = false,
                orders = ordersUiState,
                currentOrders = ordersUiState.map { pagingData ->
                    pagingData.filter {
                        currentState.selectedType == OrderHistoryScreenUiState.OrderSelectType.FINISHED
                                && it.orderState == OrderStatus.DONE
                                || currentState.selectedType == OrderHistoryScreenUiState.OrderSelectType.CANCELLED
                                && it.orderState == OrderStatus.CANCELED
                    }
                }
            )
        }
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(errorState = errorState, isLoading = false) }
    }

    override fun onClickBack() {
        sendNewEffect(OrderHistoryScreenUiEffect.Back)
    }

    override fun onClickTab(type: OrderHistoryScreenUiState.OrderSelectType) {
        updateState { currentState ->
            currentState.copy(
                selectedType = type,
                currentOrders = currentState.orders.map { pagingData ->
                    pagingData.filter {
                        type == OrderHistoryScreenUiState.OrderSelectType.FINISHED
                                && it.orderState == OrderStatus.DONE ||
                                type == OrderHistoryScreenUiState.OrderSelectType.CANCELLED &&
                                it.orderState == OrderStatus.CANCELED
                    }
                }
            )
        }
    }
}


