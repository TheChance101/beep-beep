package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.thechance.common.presentation.login.LoginScreenModel

val ScreenModelModule = module {

    factoryOf(::LoginScreenModel)

}