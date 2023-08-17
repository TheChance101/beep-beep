package org.thechance.common.di

import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun appModule() = module {
    includes(
        UseCaseModule,
        GatewayModule,
        ScreenModelModule,
    )
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(appModule())
}