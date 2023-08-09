package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IPushNotificationGateway


@Single
class SendNotificationUseCase(
    private val pushNotificationGateway: IPushNotificationGateway
) : ISendNotificationUseCase {
    override suspend fun sendNotification(tokens: List<String>, title: String, body: String) {
        pushNotificationGateway.sendNotification(tokens, title, body)
    }

}