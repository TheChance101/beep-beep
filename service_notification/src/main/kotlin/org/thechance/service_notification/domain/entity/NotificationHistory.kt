package org.thechance.service_notification.domain.entity

data class NotificationHistory(
    val id: String? = null,
    val title: String,
    val body: String,
    val topicId: String? = null,
    val sender: NotificationSender,
    val date: Long,
    val userId: String? = null,
    val topic: String? = null,
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