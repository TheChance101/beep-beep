package org.thechance.service_identity.data

import com.mongodb.reactivestreams.client.MongoClient
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.coroutine

@Single
class DataBaseContainer(client: MongoClient) {
    val database = client.coroutine.getDatabase(DATA_BASE_NAME)
}

private const val DATA_BASE_NAME = "beep_beep"

