package domain.usecase

import domain.entity.Notification
import domain.gateway.INotificationGateway

interface IManageNotificationsUseCase {
    suspend fun getTodayNotifications(): List<Notification>
    suspend fun getThisWeekNotifications(): List<Notification>
}

class ManageNotificationsUseCase(
    private val notificationGateway: INotificationGateway
) : IManageNotificationsUseCase {
    override suspend fun getTodayNotifications(): List<Notification> {
        return notificationGateway.getNotificationHistory().take(2)
    }

    override suspend fun getThisWeekNotifications(): List<Notification> {
        return notificationGateway.getNotificationHistory().takeLast(3)
    }
}