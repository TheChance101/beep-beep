package org.thechance.service_restaurant.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.litote.kmongo.reactivestreams.KMongo

@Module
@ComponentScan("org.thechance.service_restaurant")
class AppModule

val BeepClient = module {
    single {
        val mongosh = System.getenv("mongosh")
        val username = System.getenv("username")
        val password = System.getenv("password")
        KMongo.createClient("mongodb+srv://$username:$password@$mongosh.xbnbeii.mongodb.net/")
    }
}
