package org.thechance.service_restaurant.di

import org.koin.dsl.module

val AppModules = module {
    includes(GatewaysModule, UseCasesModule, DataBaseModule)
}
