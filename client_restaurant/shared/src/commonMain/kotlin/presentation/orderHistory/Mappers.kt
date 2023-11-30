package presentation.orderHistory

import androidx.paging.PagingData
import androidx.paging.map
import domain.entity.Meal
import domain.entity.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import presentation.mealManagement.toUIState
import presentation.order.OrderUiState
import presentation.order.toOrderMealUiState
import presentation.order.toOrderUiState
import util.formatDateTime


fun Order.toOrderHistoryUiState(): OrderUiState {
    return OrderUiState(
        orderId = id,
        orderState = orderState,
        orderMealUiStates = meals.map { it.toOrderMealUiState() },
        totalPrice = totalPrice,
        createdAt = createdAt.formatDateTime(),
    )
}

fun Flow<PagingData<Order>>.toUIState(): Flow<PagingData<OrderUiState>> {
    return this.map { pagingData -> pagingData.map { it.toOrderHistoryUiState()} }
}