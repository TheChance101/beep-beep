package presentation.order.order_history

import presentation.base.BaseInteractionListener

interface OrderHistoryScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()
    fun onClickTab(type: OrderHistoryScreenUiState.OrderSelectType)
}