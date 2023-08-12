package org.thechance.service_taxi.di

import org.koin.dsl.module
import org.thechance.service_taxi.data.gateway.DataBaseGatewayImpl
import org.thechance.service_taxi.domain.gateway.DataBaseGateway

val GatewayModule = module {
    single<DataBaseGateway> { DataBaseGatewayImpl(get()) }
}