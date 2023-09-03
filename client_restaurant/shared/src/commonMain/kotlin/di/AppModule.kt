package di

import org.koin.dsl.module

fun appModule() = module {
    includes(
        screenModelModule,
        GatewayModule,
        UseCaseModule,
        LocalStorageModule,
        NetworkModule
    )
}