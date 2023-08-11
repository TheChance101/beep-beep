package org.thechance.service_notification.data.gateway

import com.mongodb.client.model.UpdateOptions
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.addToSet
import org.litote.kmongo.`in`
import org.thechance.service_notification.data.DatabaseContainer
import org.thechance.service_notification.data.collection.GroupUser
import org.thechance.service_notification.data.collection.UserCollection
import org.thechance.service_notification.data.mappers.toCollection
import org.thechance.service_notification.data.mappers.toNotificationEntity
import org.thechance.service_notification.data.utils.isSuccessfullyUpdated
import org.thechance.service_notification.data.utils.paginate
import org.thechance.service_notification.domain.NotFoundException
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.domain.model.Notification
import org.thechance.service_notification.domain.model.NotificationRequest

@Single
class DatabaseGateway(
    private val databaseContainer: DatabaseContainer
) : IDatabaseGateway {

    private val userCollection by lazy { databaseContainer.userCollection }
    private val historyCollection by lazy { databaseContainer.historyCollection }

    override suspend fun getTokensForUserById(id: String): List<String> {
        return userCollection.findOneById(ObjectId(id))?.deviceTokens ?: throw NotFoundException("4001")
    }

    override suspend fun registerToken(userId: String, token: String): Boolean {
        return userCollection.updateOneById(
            id = ObjectId(userId),
            update = addToSet(UserCollection::deviceTokens, token),
            options = UpdateOptions().upsert(true)
        ).isSuccessfullyUpdated()
    }

    override suspend fun getUsersGroupIds(userGroup: String): List<String> {
        val collection = databaseContainer.database.getCollection<GroupUser>(userGroup)
        return collection.find().toList().map { it.userId.toHexString() }
    }

    override suspend fun getUsersTokens(ids: List<String>): List<String> {
        return userCollection.find(UserCollection::id `in` ids.map { ObjectId(it) }).toList()
            .flatMap { it.deviceTokens }
    }

    override suspend fun addUserToGroup(userId: String, userGroup: String): Boolean {
        val collection = databaseContainer.database.getCollection<GroupUser>(userGroup)
        return collection.insertOne(GroupUser(userId = ObjectId(userId))).wasAcknowledged()
    }

    override suspend fun addNotificationToUserHistory(notification: NotificationRequest) {
        historyCollection.insertOne(notification.toCollection())
    }

    override suspend fun getNotificationHistory(page: Int, limit: Int): List<Notification> {
        return historyCollection.find().paginate(page, limit).toList().toNotificationEntity()
    }

}