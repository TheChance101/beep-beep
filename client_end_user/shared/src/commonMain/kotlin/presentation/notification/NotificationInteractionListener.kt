package presentation.notification

import presentation.base.BaseInteractionListener

interface NotificationInteractionListener : BaseInteractionListener {
    fun onClickNotification(topicId: String, sender: Int)
    fun onClickLogin()
}