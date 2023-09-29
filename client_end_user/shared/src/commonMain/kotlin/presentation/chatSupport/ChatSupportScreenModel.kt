package presentation.chatSupport

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Message
import domain.entity.Ticket
import domain.usecase.GetTicketsUseCase
import domain.usecase.ManageMessagesUseCase
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class ChatSupportScreenModel(
    private val messageManagement: ManageMessagesUseCase,
    private val getTicketsUseCase: GetTicketsUseCase
) : BaseScreenModel<ChatUIState, ChatSupportUiEffect>(ChatUIState()),
    ChatSupportInteractionListener {

    override val viewModelScope = coroutineScope

    init {
        println("hello")
        getTickets()
    }

    private fun getTickets() {
        println("hello tickets")
        tryToCollect(
            { getTicketsUseCase() },
            ::onGetTicketsSuccess,
            ::onError
        )
    }

    private fun onGetTicketsSuccess(ticket: Ticket) {
        println("hello tickets success")
        updateState { it.copy(ticketId = ticket.id) }
        getMessages(ticket.id)
    }

    private fun getMessages(ticketId: String) {
        tryToCollect(
            { messageManagement.getMessages(ticketId) },
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
                messageManagement.sendMessage(
                    message,
                    userId,
                    ticketId = state.value.ticketId
                )
            },
            ::onSendMessageSuccess,
            ::onError
        )
    }

    override fun onClickBack() {
        sendNewEffect(ChatSupportUiEffect.NavigateUp)
    }

    private fun onSendMessageSuccess(unit: Unit) {
        updateState { it.copy(message = "") }
    }

    private fun onError(error: ErrorState) {
        println("hello error $error")
    }
}