package org.thechance.service_restaurant.usecase.cuisine

import org.koin.core.annotation.Single

@Single
data class CuisineUseCasesContainer(
    val addCuisineUseCase: AddCuisineUseCase,
    val deleteCuisineUseCase: DeleteCuisineUseCase,
    val getCuisinesUseCase: GetCuisinesUseCase,
    val getCuisineByIdUseCase: GetCuisineByIdUseCase,
    val updateCuisineUseCase: UpdateCuisineUseCase
)
