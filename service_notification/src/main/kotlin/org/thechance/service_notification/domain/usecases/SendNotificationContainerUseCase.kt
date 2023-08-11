package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.domain.gateway.IPushNotificationGateway
import org.thechance.service_notification.domain.model.NotificationRequest

@Single
class SendNotificationContainerUseCase(
    private val pusNotificationGateway: IPushNotificationGateway,
    private val databaseGateway: IDatabaseGateway,
) : ISendNotificationContainerUseCase {

    private suspend fun sendNotification(tokens: List<String>, title: String, body: String): Boolean {
        return pusNotificationGateway.sendNotification(tokens, title, body)
    }

    override suspend fun sendNotificationToUser(userId: String, title: String, body: String): Boolean {
        val tokens = databaseGateway.getUsersTokens(listOf(userId))
        return sendNotification(tokens, title, body).also {
            databaseGateway.addNotificationToUserHistory(
                NotificationRequest(
                    title = title,
                    body = body,
                    date = System.currentTimeMillis(),
                    userIds = listOf(userId),
                )
            )
        }
    }

    override suspend fun sendNotificationToUsersGroup(usersGroup: String, title: String, body: String): Boolean {
        val ids = databaseGateway.getUsersGroupIds(usersGroup)
        val tokens = databaseGateway.getUsersTokens(ids)
        return sendNotification(tokens, title, body).also {
            databaseGateway.addNotificationToUserHistory(
                NotificationRequest(
                    title = title,
                    body = body,
                    date = System.currentTimeMillis(),
                    userIds = ids,
                )
            )
        }
    }
}

interface ISendNotificationContainerUseCase {
    suspend fun sendNotificationToUser(userId: String, title: String, body: String): Boolean

    suspend fun sendNotificationToUsersGroup(usersGroup: String, title: String, body: String): Boolean
}