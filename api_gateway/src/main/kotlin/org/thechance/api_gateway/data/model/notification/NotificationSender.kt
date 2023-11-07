package org.thechance.api_gateway.data.model.notification

enum class NotificationSender(val code: Int) {
    RESTAURANT(0),
    DELIVERY(1),
    TAXI(2),
    UNDEFINED(3);

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

