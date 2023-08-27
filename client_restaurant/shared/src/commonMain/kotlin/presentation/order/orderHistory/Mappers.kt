package presentation.order.orderHistory

import domain.entity.Order
import presentation.order.OrderUiState
import presentation.order.toOrderMealUiState
import util.formatDateTime


fun Order.toOrderHistoryUiState(): OrderUiState {
    return OrderUiState(
        id = id,
        orderMealUiStates = meals.map { it.toOrderMealUiState() },
        totalPrice = totalPrice,
        createdAt = createdAt.formatDateTime(),
    )
}