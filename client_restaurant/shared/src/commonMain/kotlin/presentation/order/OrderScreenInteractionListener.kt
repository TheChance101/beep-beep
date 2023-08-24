package presentation.order

import presentation.base.BaseInteractionListener

interface OrderScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()
    fun onClickFinishOrder()
    fun onClickCancelOrder()
    fun onClickApproveOrder()
}