package org.thechance.service_notification.data.gateway

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.addToSet
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
import org.thechance.service_notification.data.collection.UserCollection
import org.thechance.service_notification.data.mappers.toCollection
import org.thechance.service_notification.data.mappers.toEntity
import org.thechance.service_notification.data.utils.isSuccessfullyUpdated
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.domain.NotFoundException
import org.thechance.service_notification.domain.model.User

@Single
class DatabaseGateway(private val userCollection: CoroutineCollection<UserCollection>) : IDatabaseGateway {

    override suspend fun createUser(user: User): Boolean {
        return userCollection.insertOne(user.toCollection()).wasAcknowledged()
    }

    override suspend fun getUsers(): List<User> {
        return userCollection.find().toList().toEntity()
    }

    override suspend fun getUser(id: String): User {
        return userCollection.findOneById(ObjectId(id))?.toEntity()
            ?: throw IllegalArgumentException("Id was not found")
    }

    override suspend fun addTokenToUser(id: String, token: String): Boolean {
        return userCollection.updateOne(
            filter = UserCollection::id eq ObjectId(id),
            update = addToSet(UserCollection::deviceTokens, token)
        ).isSuccessfullyUpdated()
    }

    override suspend fun getUserTokensById(id: String): List<String> {
        return userCollection.findOneById(ObjectId(id))?.deviceTokens ?: throw NotFoundException("4001")
    }

}