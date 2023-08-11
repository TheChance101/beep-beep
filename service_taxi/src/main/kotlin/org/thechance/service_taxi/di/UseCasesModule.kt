package org.thechance.service_taxi.di

import org.koin.dsl.module
import org.thechance.service_taxi.domain.usecase.DiscoverTripsUseCase
import org.thechance.service_taxi.domain.usecase.IDiscoverTripsUseCase
import org.thechance.service_taxi.domain.usecase.IManageTaxiUseCase
import org.thechance.service_taxi.domain.usecase.IManageTripsUseCase
import org.thechance.service_taxi.domain.usecase.ManageTaxiUseCase
import org.thechance.service_taxi.domain.usecase.ManageTripsUseCase
import org.thechance.service_taxi.domain.util.Validations

val UseCasesModule = module {
    single<IDiscoverTripsUseCase> { DiscoverTripsUseCase(get(), get()) }
    single<IManageTripsUseCase> { ManageTripsUseCase(get(), get()) }
    single<IManageTaxiUseCase> { ManageTaxiUseCase(get(), get()) }
    single { Validations() }
}