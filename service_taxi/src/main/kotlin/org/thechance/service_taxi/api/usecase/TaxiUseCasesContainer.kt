package org.thechance.service_taxi.api.usecase

import org.koin.core.annotation.Single

@Single
data class TaxiUseCasesContainer(
    val addTaxiUseCase: AddTaxiUseCase,
    val deleteTaxiUseCase: DeleteTaxiUseCase,
    val getTaxiByIdUseCase: GetTaxiByIdUseCase,
    val getAllTaxesUseCase: GetAllTaxesUseCase,
    val updateTaxiByIdUseCase: UpdateTaxiByIdUseCase,
)
