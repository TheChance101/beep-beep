package org.thechance.service_chat.data

import com.mongodb.reactivestreams.client.MongoClient
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.thechance.service_chat.data.collection.TicketCollection

@Single
class DataBaseContainer(client: MongoClient) {

    private val database = client.coroutine.getDatabase(DATA_BASE_NAME)

    val ticketCollection: CoroutineCollection<TicketCollection> = database.getCollection(CHAT_COLLECTION)

    companion object {
        private val DATA_BASE_NAME = System.getenv("DB_NAME").toString()
        const val CHAT_COLLECTION = "chat"
    }
}