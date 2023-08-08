package org.thechance.service_notification.data.gateway

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.addToSet
import org.litote.kmongo.eq
import org.thechance.service_notification.data.DatabaseContainer
import org.thechance.service_notification.data.collection.UserCollection
import org.thechance.service_notification.data.mappers.toCollection
import org.thechance.service_notification.data.mappers.toEntity
import org.thechance.service_notification.data.utils.isSuccessfullyUpdated
import org.thechance.service_notification.domain.gateway.IUserGateway
import org.thechance.service_notification.domain.model.User

@Single
class UserGateway(private val databaseContainer: DatabaseContainer) : IUserGateway {
    override suspend fun createUser(user: User): Boolean {
        return databaseContainer.userCollection.insertOne(user.toCollection()).wasAcknowledged()
    }

    override suspend fun getUsers(): List<User> {
        return databaseContainer.userCollection.find().toList().toEntity()
    }

    override suspend fun getUser(id: String): User {
        return databaseContainer.userCollection.findOneById(ObjectId(id))?.toEntity()
            ?: throw IllegalArgumentException("Id was not found")
    }

    override suspend fun addTokenToUser(id: String, token: String): Boolean {
        return databaseContainer.userCollection.updateOne(
            filter = UserCollection::id eq ObjectId(id),
            update = addToSet(UserCollection::deviceTokens, token)
        ).isSuccessfullyUpdated()
    }
}