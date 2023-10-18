package data.gateway.remote

import domain.entity.Notification
import domain.gateway.INotificationGateway

class NotificationGateway : INotificationGateway {
    override suspend fun getNotificationHistory(): List<Notification> {
        TODO("Not yet implemented")
    }
}