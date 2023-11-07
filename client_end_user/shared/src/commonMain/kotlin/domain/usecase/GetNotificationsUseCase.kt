package domain.usecase

import domain.entity.Notification
import domain.gateway.INotificationGateway

interface IGetNotificationsUseCase {
    suspend fun getTodayNotifications(): List<Notification>
    suspend fun getThisWeekNotifications(): List<Notification>
}

class GetNotificationsUseCase(
    private val notificationGateway: INotificationGateway
) : IGetNotificationsUseCase {
    override suspend fun getTodayNotifications(): List<Notification> {
        return notificationGateway.getNotificationHistory().take(2)
    }

    override suspend fun getThisWeekNotifications(): List<Notification> {
        return notificationGateway.getNotificationHistory().takeLast(3)
    }
}
