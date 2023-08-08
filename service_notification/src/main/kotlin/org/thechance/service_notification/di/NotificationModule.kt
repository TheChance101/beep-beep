package org.thechance.service_notification.di

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
        KMongo.createClient("mongodb+srv://sheshox:Shstart7@mycluster.nh7kzst.mongodb.net/?retryWrites=true&w=majority")
    }

    single {
        val databaseName = "TheChanceBeepBeep"
        get<MongoClient>().coroutine.getDatabase(databaseName)
    }

    single {
        val userCollection = "users"
        get<CoroutineDatabase>().getCollection<UserCollection>(userCollection)
    }

    single {
        val taxiCollection = "taxi_users"
        get<CoroutineDatabase>().getCollection<UserCollection>(taxiCollection)
    }

    single {
        val restaurantCollection = "restaurant_users"
        get<CoroutineDatabase>().getCollection<UserCollection>(restaurantCollection)
    }

    single {
        val deliveryCollection = "delivery_users"
        get<CoroutineDatabase>().getCollection<UserCollection>(deliveryCollection)
    }

    single {
        val endUserCollection = "end_users"
    }

    single {
        val supportCollection = "support_users"
        get<CoroutineDatabase>().getCollection<UserCollection>(supportCollection)
    }

    single {
        val dashboardCollection = "dashboard_users"
        get<CoroutineDatabase>().getCollection<UserCollection>(dashboardCollection)
    }
}

val firebaseModule = module {
    single {
        FirebaseMessaging.getInstance()
    }
}