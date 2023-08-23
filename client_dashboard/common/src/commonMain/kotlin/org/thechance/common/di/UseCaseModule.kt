package org.thechance.common.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thechance.common.domain.usecase.GetTaxisUseCase
import org.thechance.common.domain.usecase.GetUserInfoUseCase
import org.thechance.common.domain.usecase.GetUsersUseCase
import org.thechance.common.domain.usecase.IGetTaxisUseCase
import org.thechance.common.domain.usecase.GetRestaurantsUseCase
import org.thechance.common.domain.usecase.IGetRestaurantsUseCase
import org.thechance.common.domain.usecase.IGetUserInfoUseCase
import org.thechance.common.domain.usecase.IGetUsersUseCase

import org.thechance.common.domain.usecase.*

val UseCaseModule = module {
    singleOf(::GetUserInfoUseCase) { bind<IGetUserInfoUseCase>() }
    singleOf(::GetUsersUseCase) { bind<IGetUsersUseCase>() }
    singleOf(::GetTaxisUseCase) { bind<IGetTaxisUseCase>() }
    singleOf(::CreateNewTaxiUseCase) { bind<ICreateNewTaxiUseCase>() }
    singleOf(::GetRestaurantsUseCase) { bind<IGetRestaurantsUseCase>() }
}
