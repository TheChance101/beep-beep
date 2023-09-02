package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.login.LoginScreenModel

val screenModelsModule = module {
    factoryOf(::LoginScreenModel)
}