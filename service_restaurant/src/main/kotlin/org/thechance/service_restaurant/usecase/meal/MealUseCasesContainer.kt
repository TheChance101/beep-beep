package org.thechance.service_restaurant.usecase.meal

import org.koin.core.annotation.Single

@Single
data class MealUseCasesContainer(
    val addMealUseCase: AddMealUseCase,
    val deleteMealUseCase: DeleteMealUseCase,
    val getMealByIdUseCase: GetMealByIdUseCase,
    val getMealsUseCase: GetMealsUseCase,
    val updateMealUseCase: UpdateMealUseCase,
    val addCuisineToMealUseCase: AddCuisineToMealUseCase,
    val deleteCuisineFromMealUseCase: DeleteCuisineFromMealUseCase,
    val getMealCuisinesUseCase: GetMealCuisinesUseCase
)
