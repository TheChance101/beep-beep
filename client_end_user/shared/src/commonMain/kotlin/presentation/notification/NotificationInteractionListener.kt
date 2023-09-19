package presentation.notification

import presentation.base.BaseInteractionListener

interface NotificationInteractionListener : BaseInteractionListener {
    fun onClickTrackOrder()
    fun onClickTryAgain()
}