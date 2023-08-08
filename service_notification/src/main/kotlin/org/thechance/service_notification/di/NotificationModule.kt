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
        KMongo.createClient("mongodb+srv://sadeq:01990199055@cluster0.ejfkr.mongodb.net/?retryWrites=true&w=majority")
    }

    single {
        get<MongoClient>().coroutine.getDatabase("TheChanceBeepBeep")
    }

    single {
        get<CoroutineDatabase>().getCollection<NotificationHistoryCollection>("history_notification")
    }

    single(named("taxi_users")) {
        get<CoroutineDatabase>().getCollection<UserCollection>("taxi_users")
    }

    single(named("restaurant_users")) {
        get<CoroutineDatabase>().getCollection<UserCollection>("restaurant_users")
    }

    single(named("delivery_users")) {
        get<CoroutineDatabase>().getCollection<UserCollection>("delivery_users")
    }

    single(named("end_users")) {
        get<CoroutineDatabase>().getCollection<UserCollection>("end_users")
    }

    single(named("support_users")) {
        get<CoroutineDatabase>().getCollection<UserCollection>("support_users")
    }

    single(named("dashboard_users")) {
        get<CoroutineDatabase>().getCollection<UserCollection>("dashboard_users")
    }
}

val firebaseModule = module {
    single {
        FirebaseMessaging.getInstance()
    }
}