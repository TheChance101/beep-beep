package org.thechance.service_restaurant.di

import org.koin.dsl.module


val BeepClient = module {
    includes(
        DataBaseModule,
        UseCasesModule,
        GatewaysModule,
        HelperModule
    )
}

