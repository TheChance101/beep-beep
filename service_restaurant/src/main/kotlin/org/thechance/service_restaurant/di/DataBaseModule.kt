package org.thechance.service_restaurant.di

import org.koin.dsl.module
import org.litote.kmongo.reactivestreams.KMongo
import org.thechance.service_restaurant.data.DataBaseContainer

val DataBaseModule = module {
    single {
        val cluster = System.getenv("cluster")
        val username = System.getenv("username")
        val password = System.getenv("password")
        KMongo.createClient("mongodb+srv://$username:$password@$cluster.mongodb.net/")
    }

    single { DataBaseContainer(get()) }
}