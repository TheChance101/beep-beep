package org.thechance.service_taxi.domain.usecase

import org.koin.core.annotation.Single

@Single
data class TaxiUseCasesContainer(
    val addTaxiUseCase: AddTaxiUseCase,
    val deleteTaxiByIdUseCase: DeleteTaxiByIdUseCase,
    val getTaxiByIdUseCase: GetTaxiByIdUseCase,
    val getAllTaxesUseCase: GetAllTaxesUseCase,
    val updateTaxiByIdUseCase: UpdateTaxiByIdUseCase,
)
