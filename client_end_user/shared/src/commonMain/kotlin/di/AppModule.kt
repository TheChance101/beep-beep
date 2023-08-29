package di

import org.koin.dsl.module

fun appModule() = module {
    includes(
        networkModule,
        gatewayModule,
        useCaseModule,
        screenModelsModule,
    )
}