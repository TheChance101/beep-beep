package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.entity.InternalServerErrorException
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.domain.gateway.IPushNotificationGateway
import org.thechance.service_notification.endpoints.TOPIC_NOT_EXISTS

interface ITopicManagementUseCase {
    suspend fun createTopic(topic: String): Boolean

    suspend fun getTopics(): List<String>

    suspend fun subscribeToTopic(topic: String, token: String): Boolean

    suspend fun unsubscribeFromTopic(topic: String, token: String): Boolean
}

@Single
class TopicManagementUseCase(
    private val pushNotificationGateway: IPushNotificationGateway,
    private val databaseGateway: IDatabaseGateway,
) : ITopicManagementUseCase {
    override suspend fun createTopic(topic: String): Boolean {
        return databaseGateway.createTopic(topic)
    }

    override suspend fun getTopics(): List<String> {
        return databaseGateway.getTopics()
    }

    override suspend fun subscribeToTopic(topic: String, token: String): Boolean {
        if (!databaseGateway.isTopicAlreadyExists(topic)) throw InternalServerErrorException(TOPIC_NOT_EXISTS)
        return pushNotificationGateway.subscribeTokenToTopic(topic, token)
    }

    override suspend fun unsubscribeFromTopic(topic: String, token: String): Boolean {
        if (!databaseGateway.isTopicAlreadyExists(topic)) throw InternalServerErrorException(TOPIC_NOT_EXISTS)
        return pushNotificationGateway.unsubscribeTokenFromTopic(topic, token)
    }

}