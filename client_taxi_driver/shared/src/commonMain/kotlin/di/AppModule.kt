package di

import org.koin.dsl.module

fun appModule() = module {
    includes(
        ScreenModelModule,
        UseCaseModule,
        GatewayModule,
    )
}
