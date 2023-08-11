package org.thechance.service_notification.data.gateway

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.addToSet
import org.litote.kmongo.eq
import org.thechance.service_notification.data.DatabaseContainer
import org.thechance.service_notification.data.collection.UserCollection
import org.thechance.service_notification.data.mappers.toCollection
import org.thechance.service_notification.data.mappers.toNotificationEntity
import org.thechance.service_notification.data.utils.isSuccessfullyUpdated
import org.thechance.service_notification.data.utils.paginate
import org.thechance.service_notification.domain.NotFoundException
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.domain.model.Notification
import org.thechance.service_notification.domain.model.NotificationRequest
import org.thechance.service_notification.domain.model.User

@Single
class DatabaseGateway(
    private val databaseContainer: DatabaseContainer
) : IDatabaseGateway {

    private val userCollection by lazy { databaseContainer.userCollection }

    private val historyCollection by lazy { databaseContainer.historyCollection }

    override suspend fun createUser(user: User): Boolean {
        return userCollection.insertOne(user.toCollection()).wasAcknowledged()
    }

    override suspend fun addTokenToUser(id: String, token: String): Boolean {
        return userCollection.updateOne(
            filter = UserCollection::id eq ObjectId(id),
            update = addToSet(UserCollection::deviceTokens, token)
        ).isSuccessfullyUpdated()
    }

    override suspend fun getTokensForUserById(id: String): List<String> {
        return userCollection.findOneById(ObjectId(id))?.deviceTokens ?: throw NotFoundException("4001")
    }

    override suspend fun addNotificationToUserHistory(notification: NotificationRequest) {
        historyCollection.insertOne(notification.toCollection())
    }

    override suspend fun getNotificationHistory(page: Int, limit: Int): List<Notification> {
        return historyCollection.find().paginate(page, limit).toList().toNotificationEntity()
    }

}