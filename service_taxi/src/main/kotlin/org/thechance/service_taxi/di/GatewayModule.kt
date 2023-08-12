package org.thechance.service_taxi.di

import org.koin.dsl.module
import org.thechance.service_taxi.data.gateway.DataBaseGatewayImpl

val GatewayModule = module {
    single { DataBaseGatewayImpl(get()) }
}