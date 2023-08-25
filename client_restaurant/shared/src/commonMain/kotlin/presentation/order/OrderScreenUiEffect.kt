package presentation.order

sealed class OrderScreenUiEffect {
    object Back : OrderScreenUiEffect()
    data class UpdateOrder(val updatedOrder: OrderUiState) : OrderScreenUiEffect()

}
