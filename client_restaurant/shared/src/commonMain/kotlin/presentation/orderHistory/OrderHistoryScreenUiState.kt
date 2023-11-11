package presentation.orderHistory

import presentation.base.ErrorState
import presentation.order.OrderUiState

data class OrderHistoryScreenUiState(
    val selectedType: OrderSelectType = OrderSelectType.FINISHED,
    val finishedOrders: List<OrderUiState> = emptyList(),
    val canceledOrders: List<OrderUiState> = emptyList(),
    val errorState: ErrorState? = null,
) {

    enum class OrderSelectType {
        FINISHED,
        CANCELLED
    }
}
