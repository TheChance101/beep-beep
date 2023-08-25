package org.thechance.common.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thechance.common.domain.usecase.*
import org.thechance.common.domain.usecase.CreateNewTaxiUseCase
import org.thechance.common.domain.usecase.FilterRestaurantsUseCase
import org.thechance.common.domain.usecase.FindTaxiByUserNameUseCase
import org.thechance.common.domain.usecase.GetTaxiReportUseCase
import org.thechance.common.domain.usecase.GetTaxisUseCase
import org.thechance.common.domain.usecase.GetUserInfoUseCase
import org.thechance.common.domain.usecase.GetUserTokensUseCase
import org.thechance.common.domain.usecase.GetUsersUseCase
import org.thechance.common.domain.usecase.ICreateNewTaxiUseCase
import org.thechance.common.domain.usecase.IFilterRestaurantsUseCase
import org.thechance.common.domain.usecase.IFindTaxiByUsernameUseCase
import org.thechance.common.domain.usecase.IGetTaxiReportUseCase
import org.thechance.common.domain.usecase.IGetTaxisUseCase
import org.thechance.common.domain.usecase.IGetUserInfoUseCase
import org.thechance.common.domain.usecase.IGetUserTokensUseCase
import org.thechance.common.domain.usecase.IGetUsersUseCase
import org.thechance.common.domain.usecase.ILoginUserUseCase
import org.thechance.common.domain.usecase.ISearchFilterRestaurantsUseCase
import org.thechance.common.domain.usecase.ISearchRestaurantsByRestaurantNameUseCase
import org.thechance.common.domain.usecase.LoginUserUseCase
import org.thechance.common.domain.usecase.SearchFilterRestaurantsUseCase
import org.thechance.common.domain.usecase.SearchRestaurantsByRestaurantNameUseCase

val UseCaseModule = module {
    singleOf(::GetUserInfoUseCase) { bind<IGetUserInfoUseCase>() }
    singleOf(::GetUsersUseCase) { bind<IGetUsersUseCase>() }
    singleOf(::GetTaxisUseCase) { bind<IGetTaxisUseCase>() }
    singleOf(::CreateNewTaxiUseCase) { bind<ICreateNewTaxiUseCase>() }
    singleOf(::FindTaxiByUserNameUseCase) { bind<IFindTaxiByUsernameUseCase>() }
    singleOf(::SearchRestaurantsByRestaurantNameUseCase) { bind<ISearchRestaurantsByRestaurantNameUseCase>() }
    singleOf(::FilterRestaurantsUseCase) { bind<IFilterRestaurantsUseCase>() }
    singleOf(::SearchFilterRestaurantsUseCase) { bind<ISearchFilterRestaurantsUseCase>() }
    singleOf(::LoginUserUseCase) { bind<ILoginUserUseCase>() }
    singleOf(::ManageRestaurantUseCase) { bind<IManageRestaurantUseCase>() }
    singleOf(::HandleLocationUseCase) { bind<IHandleLocationUseCase>() }
    singleOf(::GetUserTokensUseCase) { bind<IGetUserTokensUseCase>() }
    singleOf(::GetTaxiReportUseCase) { bind<IGetTaxiReportUseCase>() }
}