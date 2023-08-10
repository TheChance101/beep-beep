package org.thechance.service_notification.di

import com.google.firebase.messaging.FirebaseMessaging
import com.mongodb.reactivestreams.client.MongoClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.thechance.service_notification.data.collection.GroupUser
import org.thechance.service_notification.data.collection.NotificationHistoryCollection
import org.thechance.service_notification.data.collection.UserCollection

@Module
@ComponentScan("org.thechance.service_notification")
class NotificationModule

val kmongoModule = module {
    single {
        val cluster = System.getenv("cluster")
        val username = System.getenv("username")
        val password = System.getenv("password")
        KMongo.createClient("mongodb+srv://$username:$password@$cluster.mongodb.net/?retryWrites=true&w=majority")
    }

    single {
        get<MongoClient>().coroutine.getDatabase("TheChanceBeepBeep")
    }

    single {
        get<CoroutineDatabase>().getCollection<UserCollection>("users")
    }

    single {
        get<CoroutineDatabase>().getCollection<NotificationHistoryCollection>("notification_history")
    }
}

val firebaseModule = module {
    single {
        FirebaseMessaging.getInstance()
    }
}