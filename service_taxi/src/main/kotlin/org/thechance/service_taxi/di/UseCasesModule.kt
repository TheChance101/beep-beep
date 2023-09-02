package org.thechance.service_taxi.di

import org.koin.dsl.module
import org.thechance.service_taxi.domain.usecase.ClientTripsManagementUseCase
import org.thechance.service_taxi.domain.usecase.DriverTripsManagementUseCase
import org.thechance.service_taxi.domain.usecase.IClientTripsManagementUseCase
import org.thechance.service_taxi.domain.usecase.IDriverTripsManagementUseCase
import org.thechance.service_taxi.domain.usecase.IManageTaxiUseCase
import org.thechance.service_taxi.domain.usecase.IManageTripsUseCase
import org.thechance.service_taxi.domain.usecase.ManageTaxiUseCase
import org.thechance.service_taxi.domain.usecase.ManageTripsUseCase
import org.thechance.service_taxi.domain.usecase.utils.IValidations
import org.thechance.service_taxi.domain.usecase.utils.Validations

val UseCasesModule = module {
    single<IClientTripsManagementUseCase> { ClientTripsManagementUseCase(get(), get()) }
    single<IDriverTripsManagementUseCase> { DriverTripsManagementUseCase(get()) }
    single<IManageTripsUseCase> { ManageTripsUseCase(get()) }
    single<IManageTaxiUseCase> { ManageTaxiUseCase(get(), get()) }
    single<IValidations> { Validations() }
}