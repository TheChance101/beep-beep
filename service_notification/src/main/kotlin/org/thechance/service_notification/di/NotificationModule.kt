package org.thechance.service_notification.di

import com.google.firebase.messaging.FirebaseMessaging
import com.mongodb.ConnectionString
import com.mongodb.reactivestreams.client.MongoClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

@Module
@ComponentScan("org.thechance.service_notification")
class NotificationModule

val kmongoModule = module {
    single {
        val cluster = System.getenv("cluster")
        val username = System.getenv("username")
        val password = System.getenv("password")
        val connectionString =
            ConnectionString("mongodb+srv://$username:$password@$cluster.mongodb.net/")
        KMongo.createClient(connectionString)
    }

    single {
        val DATA_BASE_NAME = System.getenv("DB_NAME").toString()
        get<MongoClient>().coroutine.getDatabase(DATA_BASE_NAME)
    }
}

val firebaseModule = module {
    single {
        FirebaseMessaging.getInstance()
    }
}
