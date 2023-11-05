package domain.usecase

import domain.entity.NotificationHistory

interface IGetNotificationsUseCase {
    suspend fun getTodayNotifications(): List<NotificationHistory>
    suspend fun getThisWeekNotifications(): List<NotificationHistory>
}

class GetNotificationsUseCase(
    private val notificationGateway: INotificationGateway
) : IGetNotificationsUseCase {
    override suspend fun getTodayNotifications(): List<NotificationHistory> {
        return notificationGateway.getNotificationHistory().take(2)
    }

    override suspend fun getThisWeekNotifications(): List<NotificationHistory> {
        return notificationGateway.getNotificationHistory().takeLast(3)
    }
}
