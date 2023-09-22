package org.thechance.common.presentation.chat

data class ChatUIState(
    val messages: List<MessageUIState> = emptyList(),
    val loading: Boolean = false,
) {
    data class MessageUIState(
        val id: String,
        val message: String,
        val isMe: Boolean,
        val avatarUrl: String,
        val showAvatar: Boolean = false,
    )
}
