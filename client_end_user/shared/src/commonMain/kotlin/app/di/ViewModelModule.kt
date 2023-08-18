package app.di

import org.koin.dsl.module
import presentation.screens.home.HomeViewModel

val viewModelModule = module {
    factory { HomeViewModel() }
}