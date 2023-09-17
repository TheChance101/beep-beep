package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.main.MainScreenModel
import presentation.map.MapScreenModel

val ScreenModelModule = module {
    factoryOf(::MainScreenModel)
    factoryOf(::MapScreenModel)
}

