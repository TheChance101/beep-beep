package org.thechance.service_identity.data

import com.mongodb.reactivestreams.client.MongoClient
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.thechance.service_identity.data.collection.*
import org.thechance.service_identity.data.geteway.DataBaseGateway
import org.thechance.service_identity.data.util.DATA_BASE_NAME
import org.thechance.service_identity.data.util.USER_DETAILS_COLLECTION

@Single
class DataBaseContainer(client: MongoClient) {
    private val database = client.coroutine.getDatabase(DATA_BASE_NAME)


    val addressCollection: CoroutineCollection<AddressCollection> = database.getCollection(ADDRESS_COLLECTION_NAME)


    val userDetailsCollection: CoroutineCollection<UserDetailsCollection> =
        database.getCollection(USER_DETAILS_COLLECTION)


    val permissionCollection: CoroutineCollection<PermissionCollection> =
        database.getCollection(PERMISSION_COLLECTION_NAME)


    val userCollection: CoroutineCollection<UserCollection> =
        database.getCollection(USER_COLLECTION)

    val walletCollection: CoroutineCollection<WalletCollection> =
        database.getCollection(WALLET_COLLECTION)

    companion object {
        private const val WALLET_COLLECTION = "wallet"
        private const val ADDRESS_COLLECTION_NAME = "address"
        private const val PERMISSION_COLLECTION_NAME = "permission"
        private const val USER_COLLECTION = "user"
    }
}



