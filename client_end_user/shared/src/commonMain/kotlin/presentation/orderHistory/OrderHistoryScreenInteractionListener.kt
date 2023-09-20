package presentation.orderHistory

import presentation.base.BaseInteractionListener

interface OrderHistoryScreenInteractionListener : BaseInteractionListener {
    fun onClickTab(type: OrderScreenUiState.OrderSelectType)
}