package di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

object AppModule {

    fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
        appDeclaration()
        modules(
            networkModule,
            gatewayModule,
            useCaseModule,
            viewModelModule,
            dataSourceModule
        )
    }

}