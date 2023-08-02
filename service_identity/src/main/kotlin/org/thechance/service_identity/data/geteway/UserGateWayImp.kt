package org.thechance.service_identity.data.geteway

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.eq
import org.litote.kmongo.setTo
import org.litote.kmongo.set
import org.thechance.service_identity.data.DataBaseContainer
import org.thechance.service_identity.data.collection.UserCollection
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.gateway.UserGateWay
import org.thechance.service_identity.utils.Constants.USER_COLLECTION

@Single
class UserGateWayImp(dataBaseContainer: DataBaseContainer): UserGateWay {

    private val userCollection by lazy { dataBaseContainer.database.getCollection<UserCollection>(
        USER_COLLECTION
    ) }

    override suspend fun getUserById(id: String): User? =
        userCollection.findOne(
            UserCollection::id eq ObjectId(id),
            UserCollection::isDeleted eq false
        )?.toUser()

    override suspend fun getUsers(): List<User> =
        userCollection.find(
            UserCollection::isDeleted eq false
        ).toList().toUser()


    override suspend fun createUser(user: User): Boolean  =
        userCollection.insertOne(user.toUserCollection()).wasAcknowledged()

    override suspend fun updateUser(id: String, user: User): Boolean =
        userCollection.updateOneById(ObjectId(id), user.toUserCollection()).wasAcknowledged()

    override suspend fun deleteUser(id: String): Boolean =
        userCollection.updateOne(
            filter = UserCollection::id eq ObjectId(id),
            update = set(UserCollection::isDeleted setTo  true)
        ).wasAcknowledged()

    private fun List<UserCollection>.toUser(): List<User> {
        return this.map { it.toUser() }
    }
}
