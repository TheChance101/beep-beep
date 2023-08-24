package presentation.order

sealed class OrderScreenUiEffect {
    object Back : OrderScreenUiEffect()
    object FinishOrder : OrderScreenUiEffect()
    object CancelOrder : OrderScreenUiEffect()
    object ApproveOrder : OrderScreenUiEffect()
}
