package presentation.map

import presentation.base.BaseInteractionListener


interface MapScreenInteractionsListener : BaseInteractionListener {
    fun onAcceptClicked()
    fun onRejectClicked()
    fun onReceivedClicked()
    fun onDeliveredClicked()
    fun onCloseClicked()
}