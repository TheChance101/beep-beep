package org.thechance.service_notification.di

import com.google.firebase.messaging.FirebaseMessaging
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
        KMongo.createClient("mongodb+srv://user1:ayaseif123@cluster0.0k23cqq.mongodb.net/")
    }

    single {
        get<MongoClient>().coroutine.getDatabase("TheChanceBeepBeep")
    }
}

val firebaseModule = module {
    single {
        FirebaseMessaging.getInstance()
    }
}