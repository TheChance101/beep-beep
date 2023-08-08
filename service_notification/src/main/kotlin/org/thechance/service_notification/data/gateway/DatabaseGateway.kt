package org.thechance.service_notification.data.gateway

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.addToSet
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
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
    private val userCollection: CoroutineCollection<UserCollection>,
    private val historyCollection: CoroutineCollection<NotificationHistoryCollection>,
    private val taxiUsersCollection: CoroutineCollection<UserCollection>,
    private val restaurantUsersCollection: CoroutineCollection<UserCollection>,
    private val dashboardUsersCollection: CoroutineCollection<UserCollection>,
    private val supportUsersCollection: CoroutineCollection<UserCollection>,
    private val deliveryUsersCollection: CoroutineCollection<UserCollection>,
    private val endUsersCollection: CoroutineCollection<UserCollection>,
) : IDatabaseGateway {

    override suspend fun createUser(user: User): Boolean {
        return userCollection.insertOne(user.toCollection()).wasAcknowledged()
    }

    override suspend fun getNotificationByUserId(id: String): Notification {
        return historyCollection.find(NotificationHistoryCollection::userId eq ObjectId(id)).first()?.toEntity()
            ?: throw NotFoundException("4005")
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

    override suspend fun getUserTokensByTopic(userGroup: String): List<String> {
        return when (userGroup) {
            "taxi_users" -> {
                taxiUsersCollection.find().toList().flatMap { it.deviceTokens }
            }

            "restaurant_users" -> {
                restaurantUsersCollection.find().toList().flatMap { it.deviceTokens }
            }

            "dashboard_users" -> {
                dashboardUsersCollection.find().toList().flatMap { it.deviceTokens }
            }

            "support_users" -> {
                supportUsersCollection.find().toList().flatMap { it.deviceTokens }
            }

            "delivery_users" -> {
                deliveryUsersCollection.find().toList().flatMap { it.deviceTokens }
            }

            "end_users" -> {
                endUsersCollection.find().toList().flatMap { it.deviceTokens }
            }

            else -> {
                throw NotFoundException("NOT_VALID_COLLECTION")
            }
        }
    }

    override suspend fun addNotificationToUserHistory(notification: Notification) {
        historyCollection.insertOne(notification.toCollection())
    }

}