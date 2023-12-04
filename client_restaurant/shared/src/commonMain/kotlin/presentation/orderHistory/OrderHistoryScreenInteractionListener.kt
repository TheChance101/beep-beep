package presentation.orderHistory

import presentation.base.BaseInteractionListener

interface OrderHistoryScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()
    fun onClickTab(type: OrderHistoryScreenUiState.OrderSelectType)
}