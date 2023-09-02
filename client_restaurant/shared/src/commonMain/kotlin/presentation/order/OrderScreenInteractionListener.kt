package presentation.order

import domain.entity.OrderState
import presentation.base.BaseInteractionListener

interface OrderScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()
    fun onClickFinishOrder(orderId: String, orderState: OrderState)
    fun onClickCancelOrder(orderId: String, orderState: OrderState)
    fun onClickApproveOrder(orderId: String, orderState: OrderState)
}