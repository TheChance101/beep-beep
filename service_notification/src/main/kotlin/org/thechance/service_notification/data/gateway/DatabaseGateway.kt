package org.thechance.service_notification.data.gateway

import com.mongodb.client.model.UpdateOptions
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.thechance.service_notification.data.DatabaseContainer
import org.thechance.service_notification.data.collection.NotificationHistoryCollection
import org.thechance.service_notification.data.collection.TopicCollection
import org.thechance.service_notification.data.collection.UserCollection
import org.thechance.service_notification.data.mappers.toCollection
import org.thechance.service_notification.data.mappers.toNotificationEntity
import org.thechance.service_notification.data.utils.paginate
import org.thechance.service_notification.domain.entity.NotFoundException
import org.thechance.service_notification.domain.entity.Notification
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.endpoints.TOKENS_NOT_FOUND

@Single
class DatabaseGateway(
    private val databaseContainer: DatabaseContainer
) : IDatabaseGateway {

    private val userCollection by lazy { databaseContainer.userCollection }
    private val historyCollection by lazy { databaseContainer.historyCollection }

    override suspend fun getUserTokens(userId: String): List<String> {
        val userTokens =  userCollection.find(UserCollection::id eq ObjectId(userId)).first()?.deviceTokens
            return userTokens ?: throw NotFoundException(TOKENS_NOT_FOUND)
    }

    override suspend fun registerToken(userId: String, token: String): Boolean {
        return userCollection.updateOneById(
            id = ObjectId(userId),
            update = addToSet(UserCollection::deviceTokens, token),
            options = UpdateOptions().upsert(true)
        ).wasAcknowledged()
    }

    override suspend fun getUsersTokens(ids: List<String>): List<String> {
        return userCollection.find(UserCollection::id `in` ids.map { ObjectId(it) }).toList()
            .flatMap { it.deviceTokens }
    }

    override suspend fun createTopic(name: String): Boolean {
        return databaseContainer.topicCollection.insertOne(TopicCollection(name)).wasAcknowledged()
    }

    override suspend fun getTopics(): List<String> {
        return databaseContainer.topicCollection.find().toList().map { it.name }
    }

    override suspend fun isTopicAlreadyExists(name: String): Boolean {
        return databaseContainer.topicCollection.findOne(TopicCollection::name eq name) != null
    }

    override suspend fun addNotificationToHistory(notification: Notification) {
        historyCollection.insertOne(notification.toCollection())
    }

    override suspend fun getNotificationHistoryForUser(page: Int, limit: Int): List<Notification> {
        return historyCollection.find().paginate(page, limit).toList().toNotificationEntity()
    }

    override suspend fun getNotificationHistoryForUser(page: Int, limit: Int, userId: String): List<Notification> {
        return historyCollection.find(
            and(
                NotificationHistoryCollection::id eq ObjectId(userId),
                NotificationHistoryCollection::isDeleted eq false
            )
        ).paginate(page, limit).toList().toNotificationEntity()
    }

    override suspend fun getTotalCountsOfNotificationHistoryForUser(userId: String): Long {
        return historyCollection.countDocuments(
            and(
                NotificationHistoryCollection::userId eq userId,
                NotificationHistoryCollection::isDeleted eq false
            )
        )
    }
}