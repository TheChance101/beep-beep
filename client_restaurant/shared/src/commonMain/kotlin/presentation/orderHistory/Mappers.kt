package presentation.orderHistory

import domain.entity.Order
import presentation.order.OrderUiState
import presentation.order.toOrderMealUiState
import util.formatDateTime


fun Order.toOrderHistoryUiState(): OrderUiState {
    return OrderUiState(
        orderId = id,
        orderState = orderState,
        orderMealUiStates = meals.map { it.toOrderMealUiState() },
        totalPrice = totalPrice,
        createdAt = createdAt.formatDateTime(),
        currency = currency
    )
}
