package presentation.order

import presentation.base.BaseInteractionListener

interface OrderScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()
    fun onClickFinishOrder(orderId: String, orderStateType: OrderStateType)
    fun onClickCancelOrder(orderId: String, orderStateType: OrderStateType)
    fun onClickApproveOrder(orderId: String, orderStateType: OrderStateType)
}