package presentation.chatSupport

import presentation.base.BaseInteractionListener

interface ChatSupportInteractionListener: BaseInteractionListener {
    fun onMessageChanged(message: String)
    fun onClickSendMessage(message: String, userId: String)
    fun onClickBack()
}