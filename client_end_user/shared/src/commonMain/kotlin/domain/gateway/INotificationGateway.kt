package domain.gateway

import domain.entity.Notification

interface INotificationGateway {
    suspend fun getNotificationHistory(): List<Notification>

}