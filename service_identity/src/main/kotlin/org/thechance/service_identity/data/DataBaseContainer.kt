package org.thechance.service_identity.data

import com.mongodb.reactivestreams.client.MongoClient
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.thechance.service_identity.data.collection.*

@Single
class DataBaseContainer(client: MongoClient) {

    private val database = client.coroutine.getDatabase(DATA_BASE_NAME)


    val addressCollection: CoroutineCollection<AddressCollection> = database.getCollection(ADDRESS_COLLECTION_NAME)


    val userDetailsCollection: CoroutineCollection<UserDetailsCollection> =
        database.getCollection(USER_DETAILS_COLLECTION)


    val userCollection: CoroutineCollection<UserCollection> =
        database.getCollection(USER_COLLECTION)

    val walletCollection: CoroutineCollection<WalletCollection> =
        database.getCollection(WALLET_COLLECTION)

    companion object {
        private const val DATA_BASE_NAME = "TheChanceBeepBeep"
        private const val WALLET_COLLECTION = "wallet"
        private const val ADDRESS_COLLECTION_NAME = "address"
        private const val USER_COLLECTION = "user"
        private const val USER_DETAILS_COLLECTION = "user_details"
    }
}



