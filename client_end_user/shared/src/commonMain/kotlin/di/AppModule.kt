package di

import org.koin.dsl.module

fun appModule() = module {
    includes(
        networkModule,
        LocalStorageModule,
        gatewayModule,
        useCaseModule,
        screenModelsModule,
    )
}