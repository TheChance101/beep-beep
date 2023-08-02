package org.thechance.service_identity.data.geteway

import io.ktor.server.plugins.*
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.eq
import org.litote.kmongo.pull
import org.litote.kmongo.push
import org.thechance.service_identity.data.DataBaseContainer
import org.thechance.service_identity.data.collection.UserDetailsCollection
import org.thechance.service_identity.data.collection.toCollection
import org.thechance.service_identity.data.collection.toEntity
import org.thechance.service_identity.domain.entity.UserDetails
import org.thechance.service_identity.domain.gateway.UserDetailsGateway

@Single
class UserDetailsGatewayImpl(dataBaseContainer: DataBaseContainer) : UserDetailsGateway {

    private val userDetailsCollection by lazy {
        dataBaseContainer.database.getCollection<UserDetailsCollection>("user_details")
    }

    override suspend fun createUserDetails(user: UserDetails) {
        userDetailsCollection.insertOne(user.toCollection())
    }

    override suspend fun getUserDetails(userId: String): UserDetails {
        return userDetailsCollection.findOne(UserDetailsCollection::userId eq ObjectId(userId).toString())
            ?.toEntity() ?: throw NotFoundException("User not found")
    }

    override suspend fun updateUserDetails(user: UserDetails) {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq user.userId,
            target = user.toCollection(),
            updateOnlyNotNullProperties = true
        )
    }

    override suspend fun addPermissionToUser(userId: String, permissionId: String) {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq userId,
            update = push(UserDetailsCollection::permissions, ObjectId(permissionId))
        )
    }

    override suspend fun removePermissionFromUser(userId: String, permissionId: String) {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq userId,
            update = pull(UserDetailsCollection::permissions, ObjectId(permissionId))
        )
    }

}