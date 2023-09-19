package di

import org.koin.dsl.module
import presentation.main.MainScreenModel

val ScreenModelModule = module {
    factory { MainScreenModel(get()) }
}
