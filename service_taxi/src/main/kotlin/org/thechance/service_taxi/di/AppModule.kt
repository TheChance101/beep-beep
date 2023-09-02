package org.thechance.service_taxi.di

import org.koin.dsl.module

val AppModules = module {
    includes(DataBaseModule, GatewayModule, UseCasesModule)
}

