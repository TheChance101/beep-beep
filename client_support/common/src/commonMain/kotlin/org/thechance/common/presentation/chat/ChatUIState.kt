package org.thechance.common.presentation.chat

data class ChatUIState(
    val ticket: TicketUIState = TicketUIState(),
    val messages: List<MessageUIState> = emptyList(),
    val appbar: AppbarUIState = AppbarUIState(),
    val message: String = "",
    val loading: Boolean = false,
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
        val username: String = "",
        val isDropdownMenuExpanded: Boolean = false,
    )
}
