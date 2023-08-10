package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.domain.model.Notification


@Single
class SendNotificationToUserUseCase(
    private val sendNotificationUseCase: ISendNotificationUseCase,
    private val databaseGateway: IDatabaseGateway
) : ISendNotificationToUserUseCase {
    override suspend fun invoke(userId: String, title: String, body: String): Boolean {
        val tokens = databaseGateway.getTokensForUserById(userId)
        return sendNotificationUseCase(tokens, title, body).also {
            val notification = Notification(title, body, System.currentTimeMillis(), userId, false)
            databaseGateway.addNotificationToUserHistory(notification)
        }
    }

}

interface ISendNotificationToUserUseCase {
    suspend operator fun invoke(userId: String, title: String, body: String): Boolean
}