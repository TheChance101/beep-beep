package org.thechance.common.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.thechance.common.presentation.login.LoginScreenModel
import org.thechance.common.presentation.main.MainScreenModel
import org.thechance.common.presentation.overview.OverviewScreenModel
import org.thechance.common.presentation.restaurant.RestaurantScreenModel
import org.thechance.common.presentation.taxi.TaxiScreenModel
import org.thechance.common.presentation.users.UserScreenModel


val ScreenModelModule = module {
//    factory { LoginScreenModel() }
//    factory { MainScreenModel() }
//    factory { RestaurantScreenModel() }
//    factory { OverviewScreenModel() }
//    factory { TaxiScreenModel() }
//    factory { UserScreenModel() }
    factoryOf(::OverviewScreenModel)
    factoryOf(::RestaurantScreenModel)
    factoryOf(::TaxiScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::UserScreenModel)
    factoryOf(::LoginScreenModel)

}