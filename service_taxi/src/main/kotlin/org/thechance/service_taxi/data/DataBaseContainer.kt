package org.thechance.service_taxi.data

import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.CoroutineClient
import org.thechance.service_taxi.utils.Constants

@Single
class DataBaseContainer(client: CoroutineClient) {
    val database by lazy { client.getDatabase(Constants.DATA_BASE_NAME) }
}