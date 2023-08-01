package org.thechance.service_identity.data
import DATABASE_NAME
import com.mongodb.reactivestreams.client.MongoClient
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.coroutine


@Single
class DataBaseContainer(client: MongoClient){
   val database =client.coroutine.getDatabase(DATABASE_NAME)
}