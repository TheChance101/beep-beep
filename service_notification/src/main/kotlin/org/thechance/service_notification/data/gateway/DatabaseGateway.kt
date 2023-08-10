package org.thechance.service_notification.data.gateway

import com.mongodb.client.model.UpdateOptions
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.addToSet
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.`in`
import org.thechance.service_notification.data.collection.GroupUser
import org.thechance.service_notification.data.collection.NotificationHistoryCollection
import org.thechance.service_notification.data.collection.UserCollection
import org.thechance.service_notification.data.mappers.toCollection
import org.thechance.service_notification.data.mappers.toEntity
import org.thechance.service_notification.data.utils.isSuccessfullyUpdated
import org.thechance.service_notification.domain.NotFoundException
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.domain.model.Notification
import org.thechance.service_notification.domain.model.User

@Single
class DatabaseGateway(
    private val database: CoroutineDatabase,
    private val userCollection: CoroutineCollection<UserCollection>,
    private val historyCollection: CoroutineCollection<NotificationHistoryCollection>,
) : IDatabaseGateway {

    override suspend fun getNotificationByUserId(id: String): Notification {
        return historyCollection.find(NotificationHistoryCollection::userId eq ObjectId(id)).first()?.toEntity()
            ?: throw NotFoundException("4005")
    }

    override suspend fun getTokensForUserById(id: String): List<String> {
        return userCollection.findOneById(ObjectId(id))?.deviceTokens ?: throw NotFoundException("4001")
    }

    override suspend fun registerToken(userId: String, token: String): Boolean {
        return userCollection.updateOneById(
            id = userId,
            update = addToSet(UserCollection::deviceTokens, token),
            options = UpdateOptions().upsert(true)
        ).isSuccessfullyUpdated()
    }

    override suspend fun getUsersGroupIds(userGroup: String): List<String> {
        val collection = database.getCollection<GroupUser>(userGroup)
        return collection.find().toList().map { it.userId }
    }

    override suspend fun getUsersTokens(ids: List<String>): List<String> {
        return userCollection.find(User::id `in` ids).toList().flatMap { it.deviceTokens }
    }

    override suspend fun addUserToGroup(userId: String, userGroup: String): Boolean {
        val collection = database.getCollection<GroupUser>(userGroup)
        return collection.insertOne(GroupUser(userId = userId)).wasAcknowledged()
    }

    override suspend fun addNotificationToUserHistory(notification: Notification) {
        historyCollection.insertOne(notification.toCollection())
    }

}