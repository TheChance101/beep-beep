package presentation.map

import presentation.base.BaseInteractionListener

interface MapScreenInteractionListener : BaseInteractionListener {
    fun onClickAccept()

    fun onClickCancel()

    fun onClickArrived()

    fun onClickDropOff()

    fun onClickCloseIcon()
}