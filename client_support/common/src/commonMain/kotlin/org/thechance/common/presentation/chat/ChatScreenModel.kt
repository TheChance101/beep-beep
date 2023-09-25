package org.thechance.common.presentation.chat

import org.thechance.common.domain.entity.Message
import org.thechance.common.domain.entity.NewMessage
import org.thechance.common.domain.entity.Ticket
import org.thechance.common.domain.usecase.IManageMessagesUseCase
import org.thechance.common.domain.usecase.IManageTicketsUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState

class ChatScreenModel(
    private val manageTicketsUseCase: IManageTicketsUseCase,
    private val manageChatUseCase: IManageMessagesUseCase,
) : BaseScreenModel<ChatUIState, ChatUIEffect>(ChatUIState()), ChatInteractionListener {

    init {
        getTickets()
    }

    // region appbar
    override fun onClickDropdownMenu() {
        updateState {
            it.copy(appbar = it.appbar.copy(isDropdownMenuExpanded = !it.appbar.isDropdownMenuExpanded))
        }
    }

    override fun onDismissDropdownMenu() {
        updateState {
            it.copy(appbar = it.appbar.copy(isDropdownMenuExpanded = false))
        }
    }

    override fun onClickLogOut() {
        sendNewEffect(ChatUIEffect.NavigateToLogin)
    }
    // endregion

    // region ticket
    private fun getTickets() {
        tryToCollect(
            manageTicketsUseCase::getTickets,
            ::onGetTicketsSuccess,
            ::onError
        )
    }

    private fun onGetTicketsSuccess(ticket: Ticket?) {
        updateState {
            it.copy(
                ticket = ticket?.toUIState() ?: it.ticket,
                idle = ticket == null,
                loading = false,
            )
        }
        ticket?.let { getMessages(it.id) }
    }

    override fun onCloseTicketClicked() {
        updateState { it.copy(loading = true) }
        tryToExecute(
            { manageTicketsUseCase.closeTicket(state.value.ticket.id) },
            ::onCloseTicketSuccess,
            ::onError
        )
    }

    private fun onCloseTicketSuccess(unit: Unit) {
        updateState { it.copy(loading = false) }
    }
    // endregion

    // region messages
    private fun getMessages(ticketId: String) {
        tryToCollect(
            { manageChatUseCase.getMessages(ticketId) },
            ::onGetNewMessage,
            ::onError
        )
    }

    private fun onGetNewMessage(message: List<Message>) {
        updateState {
            it.copy(messages = message.map { message -> message.toUIState() })
        }
    }

    override fun onMessageChange(message: String) {
        updateState {
            it.copy(message = message)
        }
    }

    override fun onSendMessageClicked() {
        tryToExecute(
            {
                manageChatUseCase.sendMessage(
                    NewMessage(
                        ticketId = state.value.ticket.id,
                        senderId = "1",
                        message = state.value.message,
                    )
                )
            },
            ::onSendMessageSuccess,
            ::onError
        )
    }

    private fun onSendMessageSuccess(unit: Unit) {
        updateState {
            it.copy(message = "")
        }
    }
    // endregion

    private fun onError(error: ErrorState) {
        updateState {
            it.copy(loading = false)
        }
    }

}