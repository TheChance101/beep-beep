package presentation.orderHistory

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import presentation.base.ErrorState
import presentation.order.OrderUiState

data class OrderHistoryScreenUiState(
    val isLoading: Boolean = false,
    val orders:Flow<PagingData<OrderUiState>> = emptyFlow(),
    val currentOrders: Flow<PagingData<OrderUiState>> = emptyFlow(),
    val selectedType: OrderSelectType = OrderSelectType.FINISHED,
    val errorState: ErrorState? = null
) {

    enum class OrderSelectType {
        FINISHED,
        CANCELLED
    }
}
