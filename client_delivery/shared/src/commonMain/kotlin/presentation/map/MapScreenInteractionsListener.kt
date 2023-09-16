package presentation.map

import presentation.base.BaseInteractionListener


interface MapScreenInteractionsListener : BaseInteractionListener {
    fun onAcceptClicked()
    fun onRejectClicked()
    fun onCloseClicked()
}