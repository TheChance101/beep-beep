package presentation.chatSupport

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Message
import domain.entity.Ticket
import domain.usecase.ChatUseCase
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class ChatSupportScreenModel(
    private val manageChat: ChatUseCase,
) : BaseScreenModel<ChatUIState, ChatSupportUiEffect>(ChatUIState()),
    ChatSupportInteractionListener {

    override val viewModelScope = coroutineScope

    init {
        getTickets()
    }

    private fun getTickets() {
        tryToExecute(
            { manageChat.openNewTicket("64fc7ac0caa12e39d6a34213") },
            ::onGetTicketsSuccess,
            ::onError
        )
    }

    private fun onGetTicketsSuccess(ticket: Ticket) {
        updateState { it.copy(ticketId = ticket.id) }
        getMessages(ticket.id)
    }

    private fun getMessages(ticketId: String) {
        tryToCollect(
            { manageChat.getMessages(ticketId) },
            ::onGetNewMessage,
            ::onError
        )
    }

    private fun onGetNewMessage(messages: List<Message>) {
        updateState {
            it.copy(messages = messages.map { message -> message.toUIState() })
        }
    }

    override fun onMessageChanged(message: String) {
        updateState { it.copy(message = message) }
    }

    override fun onClickSendMessage(message: String, userId: String) {
        println("userId: $userId")
        tryToExecute(
            {
                manageChat.sendMessage(
                    message,
                    userId,
                    ticketId = state.value.ticketId
                )
            },
            ::onSendMessageSuccess,
            ::onSendNewMessageError
        )
    }

    override fun onClickBack() {
        sendNewEffect(ChatSupportUiEffect.NavigateUp)
    }

    private fun onSendMessageSuccess(unit: Unit) {
        updateState { it.copy(message = "") }
    }

    private fun onError(error: ErrorState) {
        println("$error")
    }

    private fun onSendNewMessageError(error: ErrorState) {
        updateState { it.copy(message = "") }
        println("$error")
    }
}
