package presentation.orderHistory

import presentation.base.ErrorState
import presentation.order.OrderUiState

data class OrderHistoryScreenUiState(
    val isLoading: Boolean = false,
    val orders: List<OrderUiState> = emptyList(),
    val currentOrders: List<OrderUiState> = emptyList(),
    val selectedType: OrderSelectType = OrderSelectType.FINISHED,
    val errorState: ErrorState? = null
) {

    enum class OrderSelectType {
        FINISHED,
        CANCELLED
    }
}
