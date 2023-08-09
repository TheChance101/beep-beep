package org.thechance.service_notification.domain.usecases


interface ISendNotificationToUsersGroupUseCase {
    suspend fun sendNotificationToUsersGroup(usersGroup: String, title: String, body: String)
}