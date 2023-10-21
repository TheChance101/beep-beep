package di

import org.koin.dsl.module

fun appModule() = module {
    includes(
        networkModule,
        localStorageModule,
        gatewayModule,
        useCaseModule,
        screenModelsModule
    )
}
