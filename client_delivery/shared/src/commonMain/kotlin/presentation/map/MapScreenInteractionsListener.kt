package presentation.map

import presentation.base.BaseInteractionListener


interface MapScreenInteractionsListener : NewOrderInteractionsListener {

    fun onDeliveredClicked()
    fun onCloseClicked()
}

interface NewOrderInteractionsListener : BaseInteractionListener {
    fun onAcceptClicked()
    fun onRejectClicked()
}

interface AcceptedOrderInteractionsListener : BaseInteractionListener {
    fun onReceivedClicked()
}