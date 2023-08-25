package org.thechance.common.di

import org.koin.dsl.module

fun appModule() = module {
    includes(
        UseCaseModule,
        GatewayModule,
        ScreenModelModule,
        LocalStorageModule,
        NetworkModule
    )
}
