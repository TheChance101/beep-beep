package di

import org.koin.dsl.module

fun appModule() = module {
    includes(
        ScreenModelModule,
        GatewayModule,
        UseCaseModule,
        LocalStorageModule,
        NetworkModule
    )
}
