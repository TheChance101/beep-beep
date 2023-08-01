package org.thechance.service_identity.data.geteway

import USER_COLLECTION
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.thechance.service_identity.data.DataBaseContainer
import org.thechance.service_identity.data.collection.UserCollection
import org.thechance.service_identity.domain.gateway.UserGateWay
import org.thechance.service_identity.entity.User

@Single
class UserGateWayImp(dataBaseContainer: DataBaseContainer): UserGateWay {

    private val userCollection by lazy { dataBaseContainer.database.getCollection<UserCollection>(
        USER_COLLECTION
    ) }

    override suspend fun getUserById(id: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun getUsers(): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun createUser(user: User): Boolean {
        return  userCollection.insertOne(user.toCollection()).wasAcknowledged()
    }

    override suspend fun updateUser(id: String, user: User): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: String): Boolean {
        TODO("Not yet implemented")
    }


    private fun User.toCollection(): UserCollection {
        return UserCollection(
            id = ObjectId(this.id),
            fullName = this.fullName,
            username = this.username,
            isDeleted = this.isDeleted,
        )
    }

}