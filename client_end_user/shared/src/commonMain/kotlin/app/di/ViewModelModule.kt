package app.di

import org.koin.dsl.module
import presentation.screens.HomeViewModel

val viewModelModule = module {
    factory { HomeViewModel() }
}