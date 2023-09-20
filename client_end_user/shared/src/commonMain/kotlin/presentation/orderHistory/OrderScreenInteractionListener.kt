package presentation.orderHistory

import presentation.base.BaseInteractionListener

interface OrderScreenInteractionListener : BaseInteractionListener {
    fun onClickTab(type: OrderScreenUiState.OrderSelectType)
}