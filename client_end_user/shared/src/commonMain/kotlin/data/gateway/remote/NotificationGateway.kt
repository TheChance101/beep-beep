package data.gateway.remote

import domain.entity.NotificationHistory
import domain.gateway.INotificationGateway

class NotificationGateway : INotificationGateway {
    override suspend fun getNotificationHistory(): List<NotificationHistory> {
        TODO("Not yet implemented")
    }
}