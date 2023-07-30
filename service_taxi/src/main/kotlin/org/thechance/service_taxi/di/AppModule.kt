package org.thechance.service_taxi.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


@Module
@ComponentScan("org.thechance.service_taxi")
class AppModule

val kmongoModule = module {
    single {
        KMongo.createClient("mongodb+srv://mohammed1212alhams:mmhhoo123@cluster0.ol4oiit.mongodb.net/")
            .coroutine
    }
}