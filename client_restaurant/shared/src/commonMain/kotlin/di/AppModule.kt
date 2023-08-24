package di

import org.koin.dsl.module

fun appModule() = module {
    includes(
        screenModule,
        GatewayModule,
        UseCaseModule,
    )
}