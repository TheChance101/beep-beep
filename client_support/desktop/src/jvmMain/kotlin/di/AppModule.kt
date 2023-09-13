package di

import org.koin.dsl.module

fun appModule() = module {
    includes(
        LocalStorageModule,
        NetworkModule,
        GatewayModule,
        UseCaseModule,
        ScreenModelModule
    )
}