package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.data.gateway.DatabaseGateway
import org.thechance.service_notification.domain.entity.NotFoundException
import org.thechance.service_notification.domain.entity.ResourceAlreadyExistsException
import org.thechance.service_notification.domain.gateway.IPushNotificationGateway
import org.thechance.service_notification.endpoints.ALREADY_EXISTS_TOPIC
import org.thechance.service_notification.endpoints.TOPIC_NOT_EXISTS


interface ITopicManagementUseCase {
    suspend fun subscribeToTopic(topicName: String, token: String): Boolean

    suspend fun unsubscribeFromTopic(topicName: String, token: String): Boolean

    suspend fun createTopic(name: String): Boolean

    suspend fun getTopics(): List<String>
}

@Single
class TopicManagementUseCase(
    private val pusNotificationGateway: IPushNotificationGateway,
    private val databaseGateway: DatabaseGateway
) : ITopicManagementUseCase {

    override suspend fun subscribeToTopic(topicName: String, token: String): Boolean {
        if (!databaseGateway.isTopicAlreadyExists(topicName)) throw NotFoundException(TOPIC_NOT_EXISTS)
        return pusNotificationGateway.subscribeTokenToTopic(topicName, token)
    }

    override suspend fun unsubscribeFromTopic(topicName: String, token: String): Boolean {
        if (!databaseGateway.isTopicAlreadyExists(topicName)) throw NotFoundException(TOPIC_NOT_EXISTS)
        return pusNotificationGateway.unsubscribeTokenFromTopic(topicName, token)
    }

    override suspend fun createTopic(name: String): Boolean {
        if (databaseGateway.isTopicAlreadyExists(name)) throw ResourceAlreadyExistsException(ALREADY_EXISTS_TOPIC)
        return databaseGateway.createTopic(name)
    }

    override suspend fun getTopics(): List<String> {
        return databaseGateway.getTopics()
    }

}