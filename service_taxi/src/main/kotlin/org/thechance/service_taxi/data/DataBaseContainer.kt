package org.thechance.service_taxi.data

import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.CoroutineClient

@Single
class DataBaseContainer(client: CoroutineClient) {
    val db by lazy { client.getDatabase(System.getenv("DB_NAME")) }
}