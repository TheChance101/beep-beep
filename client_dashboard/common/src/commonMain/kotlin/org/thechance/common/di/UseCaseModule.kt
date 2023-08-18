package org.thechance.common.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thechance.common.domain.usecase.GetUserInfoUseCase
import org.thechance.common.domain.usecase.IGetUserInfoUseCase


val UseCaseModule = module {
    singleOf(::GetUserInfoUseCase) { bind<IGetUserInfoUseCase>() }
}