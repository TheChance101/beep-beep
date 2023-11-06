package domain.entity

data class NotificationHistory(
    val id: String,
    val topicId: String,
    val title: String,
    val sender: NotificationSender,
    val body: String,
    val date: Date,
    val time: Time,
    val userId: String,
    val topic: String,
) {
    enum class NotificationSender(val code: Int) {
        RESTAURANT(0),
        DELIVERY(1),
        TAXI(2),
        UNDEFINED(4);
    }

    companion object {
        fun getNotificationSender(code: Int): NotificationSender {
            NotificationSender.values().forEach {
                if (it.code == code) {
                    return it
                }
            }
            return NotificationSender.UNDEFINED
        }
    }
}