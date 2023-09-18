package domain.usecase

import data.gateway.remote.FakeRemoteGateway
import domain.entity.Notification

interface IManageNotificationsUseCase {
    suspend fun getTodayNotifications(): List<Notification>
    suspend fun getThisWeekNotifications(): List<Notification>
}

class ManageNotificationsUseCase(private val remoteGateway: FakeRemoteGateway) :
    IManageNotificationsUseCase {
    override suspend fun getTodayNotifications(): List<Notification> {
        return remoteGateway.getNotificationHistory()
    }

    override suspend fun getThisWeekNotifications(): List<Notification> {
        return remoteGateway.getNotificationHistory()
    }

}