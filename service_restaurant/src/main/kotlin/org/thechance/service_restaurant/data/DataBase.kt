package org.thechance.service_restaurant.data

import com.mongodb.reactivestreams.client.MongoClient
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.coroutine
import org.thechance.service_restaurant.utils.DATABASE_NAME

@Single
class DataBase(client: MongoClient) {

    val database = client.coroutine.getDatabase(DATABASE_NAME)

}