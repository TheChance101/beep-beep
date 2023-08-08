package org.thechance.service_notification.di

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.mongodb.reactivestreams.client.MongoClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.thechance.service_notification.data.collection.UserCollection

@Module
@ComponentScan("org.thechance.service_notification")
class NotificationModule

val kmongoModule = module {
    single {
        val cluster = System.getenv("cluster")
        val username = System.getenv("username")
        val password = System.getenv("password")
        KMongo.createClient("mongodb+srv://$username:$password@$cluster.mongodb.net/")
    }
    single {
        val databaseName = "TheChanceBeepBeep"
        get<MongoClient>().coroutine.getDatabase(databaseName)
    }
    single {
        val userCollection = "users"
        get<CoroutineDatabase>().getCollection<UserCollection>(userCollection)
    }
}

val firebaseModule = module {
    single {
        val firebaseKey = System.getenv("firebaseKey")
        val serviceAccount = this::class.java.classLoader.getResourceAsStream("beep-beep-12cae-firebase-adminsdk-hfmvb-ec3fac5f3d.json")
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build()
        FirebaseApp.initializeApp(options)
    }

    single {
        FirebaseMessaging.getInstance()
    }
}

