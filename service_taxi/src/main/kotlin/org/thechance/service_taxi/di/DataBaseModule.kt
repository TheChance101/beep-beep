package org.thechance.service_taxi.di

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.thechance.service_taxi.data.DataBaseContainer

val DataBaseModule = module {
    single {
        val cluster = System.getenv("cluster")
        val username = System.getenv("username")
        val password = System.getenv("password")
        val connectionString = ConnectionString("mongodb+srv://$username:$password@$cluster.mongodb.net/")
        val settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()
        KMongo.createClient(settings).coroutine
    }
    single { DataBaseContainer(get()) }
}