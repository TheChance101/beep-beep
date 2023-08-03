package org.thechance.service_identity.data.geteway

import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.thechance.service_identity.data.DataBaseContainer
import org.thechance.service_identity.data.collection.DetailedUserCollection
import org.thechance.service_identity.data.collection.UserCollection
import org.thechance.service_identity.data.collection.UserDetailsCollection
import org.thechance.service_identity.data.collection.toEntity
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.gateway.UserGateWay
import org.thechance.service_identity.utils.Constants.USER_COLLECTION
import org.thechance.service_identity.utils.isDocumentModified

@Single
class UserGateWayImp(dataBaseContainer: DataBaseContainer): UserGateWay {

    private val userCollection by lazy {
        dataBaseContainer.database.getCollection<UserCollection>(
            USER_COLLECTION
        )
    }

    private val userDetailsCollection by lazy { dataBaseContainer.database.getCollection<UserDetailsCollection>("user_details") }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            createUniqueIndexIfNotExists()
        }
    }

    private suspend fun createUniqueIndexIfNotExists() {
        if (!isUniqueIndexCreated()) {
            val indexOptions = IndexOptions().unique(true)
            userCollection.createIndex(Indexes.ascending("username"), indexOptions)
        }
    }

    private suspend fun isUniqueIndexCreated(): Boolean {
        val indexName = "username_1"

        val indexInfo = userCollection.listIndexes<Indexes>().toList()
            .filter { it.equals(indexName) }

        return indexInfo.isNotEmpty()
    }

    override suspend fun getUserById(id: String): User? {
        return userCollection.aggregate<DetailedUserCollection>(
            match(UserCollection::id eq ObjectId(id)),
            lookup(
                localField = "_id",
                from = "user_details",
                foreignField = "userId",
                newAs = "details"
            )
        ).toList().toEntity().firstOrNull()
    }

    override suspend fun getDetailedUsers(): List<User> {
        return userCollection.aggregate<DetailedUserCollection>(
            lookup(
                localField = "_id",
                from = "user_details",
                foreignField = "userId",
                newAs = "details"
            )
        ).toList().toEntity()
    }

    override suspend fun getUsers(): List<User> =
        userCollection.find(
            UserCollection::isDeleted eq false
        ).toList().toUser()


    override suspend fun createUser(user: User): Boolean {
        val userDocument = user.toUserCollection()
        userDetailsCollection.insertOne(user.toDetailsCollection(userDocument.id.toHexString()))
        return userCollection.insertOne(userDocument).wasAcknowledged()
    }

    override suspend fun updateUser(id: String, user: User): Boolean {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq ObjectId(id),
            target = user.toDetailsCollection(id),
            updateOnlyNotNullProperties = true
        )
        return userCollection.updateOneById(
            ObjectId(id),
            user.toUserCollection(),
            updateOnlyNotNullProperties = true
        ).isDocumentModified()
    }

    override suspend fun deleteUser(id: String): Boolean {
        return userCollection.updateOne(
            filter = UserCollection::id eq ObjectId(id),
            update = set(UserCollection::isDeleted setTo true)
        ).isDocumentModified()
    }

    override suspend fun addPermissionToUser(userId: String, permissionId: String) {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq ObjectId(userId),
            update = push(UserDetailsCollection::permissions, ObjectId(permissionId))
        )
    }

    override suspend fun removePermissionFromUser(userId: String, permissionId: String) {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq ObjectId(userId),
            update = pull(UserDetailsCollection::permissions, ObjectId(permissionId))
        )
    }

    private fun List<UserCollection>.toUser(): List<User> {
        return this.map { it.toUser() }
    }
}
