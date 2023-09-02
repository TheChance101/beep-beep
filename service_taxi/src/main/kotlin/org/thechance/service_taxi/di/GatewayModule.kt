package org.thechance.service_taxi.di

import org.koin.dsl.module
import org.thechance.service_taxi.data.gateway.TaxiGateway
import org.thechance.service_taxi.domain.gateway.ITaxiGateway

val GatewayModule = module {
    single<ITaxiGateway> { TaxiGateway(get()) }
}