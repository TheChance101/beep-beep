package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.main.MainScreenModel

val ScreenModelModule = module {
    factoryOf(::MainScreenModel)
}

