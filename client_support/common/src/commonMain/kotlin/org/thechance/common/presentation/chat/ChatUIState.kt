package org.thechance.common.presentation.chat

import org.thechance.common.domain.entity.Message
import org.thechance.common.domain.entity.Ticket

data class ChatUIState(
    val ticket: TicketUIState = TicketUIState(),
    val messages: List<MessageUIState> = emptyList(),
    val appbar: AppbarUIState = AppbarUIState(),
    val message: String = "",
    val loading: Boolean = true,
    val idle: Boolean = false,
) {
    data class TicketUIState(
        val id: String = "",
        val username: String = "",
        val userAvatarUrl: String = "",
        val openedAt: String = "",
    )

    data class MessageUIState(
        val id: String,
        val message: String,
        val isMe: Boolean,
        val avatarUrl: String,
        val showAvatar: Boolean = false,
    )

    data class AppbarUIState(
        val username: String = "John Doe",
        val isDropdownMenuExpanded: Boolean = false,
    )
}

// region Mappers

fun Message.toUIState(): ChatUIState.MessageUIState {
    return ChatUIState.MessageUIState(
        id = id,
        message = message,
        isMe = isMe,
        avatarUrl = avatarUrl,
    )
}

fun Ticket.toUIState(): ChatUIState.TicketUIState {
    return ChatUIState.TicketUIState(
        id = id,
        username = username,
        userAvatarUrl = avatar,
        openedAt = openedAt,
    )
}

fun List<Ticket>.toUIState(): List<ChatUIState.TicketUIState> {
    return map { it.toUIState() }
}

// endregion