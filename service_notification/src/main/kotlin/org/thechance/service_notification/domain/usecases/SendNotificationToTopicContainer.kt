package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.data.gateway.DatabaseGateway
import org.thechance.service_notification.domain.gateway.IPushNotificationGateway

@Single
class SendNotificationToTopicContainer(
    private val pusNotificationGateway: IPushNotificationGateway,
    private val databaseGateway: DatabaseGateway
) : ISendNotificationToTopicContainer {

    override suspend fun sendNotificationToTopic(topic: String, title: String, body: String): Boolean {
        return pusNotificationGateway.sendNotificationToTopic(topic, title, body)
    }

    override suspend fun subscribeTokenToTopic(topicName: String, token: String): Boolean {
        return pusNotificationGateway.subscribeTokenToTopic(topicName, token)
    }

    override suspend fun unsubscribeTokenFromTopic(topicName: String, token: String): Boolean {
        return pusNotificationGateway.unsubscribeTokenFromTopic(topicName, token)
    }

    override suspend fun createTopic(name: String) {
        databaseGateway.createTopic(name)
    }

    override suspend fun getTopics(): List<String> {
        return databaseGateway.getTopics()
    }

    override suspend fun isTopicAlreadyExists(name: String): Boolean {
        return databaseGateway.isTopicAlreadyExists(name)
    }

}


interface ISendNotificationToTopicContainer {

    suspend fun sendNotificationToTopic(topic: String, title: String, body: String): Boolean

    suspend fun subscribeTokenToTopic(topicName: String, token: String): Boolean

    suspend fun unsubscribeTokenFromTopic(topicName: String, token: String): Boolean

    suspend fun createTopic(name: String)

    suspend fun getTopics(): List<String>

    suspend fun isTopicAlreadyExists(name: String): Boolean

}