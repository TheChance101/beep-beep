package presentation.order.order_history

import presentation.base.ErrorState
import presentation.order.OrderUiState

data class OrderHistoryScreenUiState(
    val selectedType: OrderSelectType = OrderSelectType.FINISHED,
    val orders: List<OrderUiState> = emptyList(),
    val errorState: ErrorState? = null,
) {
    enum class OrderSelectType {
        FINISHED,
        CANCELLED
    }
}
