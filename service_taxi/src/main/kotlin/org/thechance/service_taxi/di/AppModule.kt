package org.thechance.service_taxi.di

import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.thechance.service_taxi.data.DataBaseContainer
import org.thechance.service_taxi.data.gateway.DataBaseGatewayImpl
import org.thechance.service_taxi.domain.gateway.DataBaseGateway
import org.thechance.service_taxi.domain.usecase.DiscoverTripsUseCase
import org.thechance.service_taxi.domain.usecase.IDiscoverTripsUseCase
import org.thechance.service_taxi.domain.usecase.IManageTaxiUseCase
import org.thechance.service_taxi.domain.usecase.IManageTripsUseCase
import org.thechance.service_taxi.domain.usecase.ManageTaxiUseCase
import org.thechance.service_taxi.domain.usecase.ManageTripsUseCase
import org.thechance.service_taxi.domain.util.Validations

val kmongoModule = module {
    single {
        val cluster = System.getenv("cluster")
        val username = System.getenv("username")
        val password = System.getenv("password")
        KMongo.createClient("mongodb+srv://$username:$password@$cluster.mongodb.net/")
            .coroutine
    }
    single { DataBaseContainer(get()) }
    single<DataBaseGateway> { DataBaseGatewayImpl(get()) }
}

val useCasesModule = module {
    single<IDiscoverTripsUseCase> { DiscoverTripsUseCase(get(), get()) }
    single<IManageTripsUseCase> { ManageTripsUseCase(get(), get()) }
    single<IManageTaxiUseCase> { ManageTaxiUseCase(get(), get()) }
    single { Validations() }
}