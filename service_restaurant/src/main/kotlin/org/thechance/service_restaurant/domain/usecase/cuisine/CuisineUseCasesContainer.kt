package org.thechance.service_restaurant.domain.usecase.cuisine

import org.koin.core.annotation.Single

@Single
data class CuisineUseCasesContainer(
    val getCuisineByIdUseCase: GetCuisineByIdUseCase,
    val getCuisinesUseCase: GetCuisinesUseCase,
    val getMealsInCuisine: GetMealsInCuisineUseCase,

    val addCuisineUseCase: AddCuisineUseCase,
    val addMealsToCuisine: AddMealsToCuisineUseCase,

    val updateCuisineUseCase: UpdateCuisineUseCase,
    val deleteCuisineUseCase: DeleteCuisineUseCase,
    val deleteMealsInCuisine: DeleteMealsIoCuisineUseCase,
)
