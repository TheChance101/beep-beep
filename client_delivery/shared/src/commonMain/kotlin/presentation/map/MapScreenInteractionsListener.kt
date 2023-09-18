package presentation.map

import presentation.base.BaseInteractionListener


interface MapScreenInteractionsListener : NewOrderInteractionsListener,
    AcceptedOrderInteractionsListener, DeliveredOrderInteractionsListener
{
    fun onCloseClicked()
}

interface NewOrderInteractionsListener : BaseInteractionListener {
    fun onAcceptClicked()
    fun onRejectClicked()
}

interface AcceptedOrderInteractionsListener : BaseInteractionListener {
    fun onReceivedClicked()
}

interface DeliveredOrderInteractionsListener : BaseInteractionListener {
    fun onDeliveredClicked()
}