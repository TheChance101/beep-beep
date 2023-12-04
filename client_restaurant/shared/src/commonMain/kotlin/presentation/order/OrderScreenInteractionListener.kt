package presentation.order

import presentation.base.BaseInteractionListener

interface OrderScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()
    fun onClickFinishOrder(orderId: String)
    fun onClickCancelOrder(orderId: String)
    fun onClickApproveOrder(orderId: String)
    fun onSnackBarDismissed()
}