package org.thechance.common.di

import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.thechance.common.ui.screen.login.LoginScreenModel

fun appModule() = module {
    factory { LoginScreenModel() }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        appModule(),
    )
}