package presentation.chatSupport

import domain.entity.Message

data class ChatUIState(
    val ticketId: String = "",
    val messages: List<MessageUIState> = emptyList(),
    val message: String = "",
)

data class MessageUIState(
    val id: String,
    val senderId: String,
    val message: String,
    val isMe: Boolean,
)

fun MessageUIState.toEntity(ticketId: String): Message{
    return Message(
        id = id,
        message = message,
        isMe = isMe,
        senderId = senderId,
        avatarUrl = "",
        ticketId = ticketId
    )
}
fun Message.toUIState(): MessageUIState {
    return MessageUIState(
        id = id,
        message = message,
        isMe = isMe,
        senderId = senderId
    )
}