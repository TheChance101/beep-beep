package org.thechance.service_notification.data.gateway

import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
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
import org.thechance.service_notification.domain.entity.NotificationHistory
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.endpoints.TOKENS_NOT_FOUND
import org.thechance.service_notification.endpoints.TOKEN_NOT_REGISTERED

@Single
class DatabaseGateway(
    private val databaseContainer: DatabaseContainer
) : IDatabaseGateway {

    private val userCollection by lazy { databaseContainer.userCollection }
    private val historyCollection by lazy { databaseContainer.historyCollection }

    override suspend fun deleteAllNotification(): Boolean {
        return try {
            userCollection.deleteMany()
            historyCollection.deleteMany()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getUserTokens(userId: String): List<String> {
        val userTokens = userCollection.findOneById(ObjectId(userId))?.deviceTokens
        return userTokens ?: throw NotFoundException(TOKENS_NOT_FOUND)
    }
    override suspend fun clearDeviceToken(userId: String, deviceToken: String): Boolean {
        val userTokens = getUserTokens(userId)
        val newUserTokens = userTokens.filterNot { it == deviceToken }
        val result = userCollection.updateOne(
            UserCollection::id eq ObjectId(userId),
            Updates.set(UserCollection::deviceTokens.name, newUserTokens)
        )
        return result.wasAcknowledged()
    }

    override suspend fun clearAllDeviceUserTokens(userId: String): Boolean {
        val result = userCollection.updateOne(
            UserCollection::id eq ObjectId(userId),
            pullAll(UserCollection::deviceTokens, listOf<String>())
        )
        if (result.wasAcknowledged() && result.modifiedCount > 0) {
            return true
        } else {
            throw NotFoundException(TOKENS_NOT_FOUND)
        }
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

    override suspend fun addNotificationToHistory(notificationHistory: NotificationHistory) {
        historyCollection.insertOne(notificationHistory.toCollection())
    }

    override suspend fun getNotificationHistoryForUser(page: Int, limit: Int): List<NotificationHistory> {
        return historyCollection.find().paginate(page, limit).toList().toNotificationEntity()
    }

    override suspend fun getNotificationHistoryForUser(
        page: Int,
        limit: Int,
        userId: String
    ): List<NotificationHistory> {
        val twentyFourHoursAgo = System.currentTimeMillis() - (24 * 60 * 60 * 1000)
        return historyCollection.find(
            and(
                NotificationHistoryCollection::userId eq userId,
                NotificationHistoryCollection::isDeleted eq false,
                NotificationHistoryCollection::date lt twentyFourHoursAgo
            )
        ).sort(ascending(NotificationHistoryCollection::date)).paginate(page, limit).toList().toNotificationEntity()
    }

    override suspend fun getNotificationHistoryInTheLast24Hours(userId: String): List<NotificationHistory> {
        val twentyFourHoursAgo = System.currentTimeMillis() - (24 * 60 * 60 * 1000)
        return historyCollection.find(
            and(
                NotificationHistoryCollection::userId eq userId,
                NotificationHistoryCollection::isDeleted eq false,
                NotificationHistoryCollection::date gte twentyFourHoursAgo
            )
        ).sort(descending(NotificationHistoryCollection::date)).toList().toNotificationEntity()
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