package domain.gateway

import domain.entity.NotificationHistory

interface INotificationGateway {
    suspend fun getNotificationHistory(): List<NotificationHistory>

}