package org.thechance.service_restaurant.domain.usecase.meal

import org.koin.core.annotation.Single

@Single
data class MealUseCasesContainer(
    val addMealUseCase: AddMealUseCase,
    val deleteMealUseCase: DeleteMealUseCase,
    val getMealByIdUseCase: GetMealByIdUseCase,
    val getMealsUseCase: GetMealsUseCase,
    val updateMealUseCase: UpdateMealUseCase,
    val addCuisinesToMealUseCase: AddCuisinesToMealUseCase,
    val deleteCuisineFromMealUseCase: DeleteCuisineFromMealUseCase,
    val getMealCuisinesUseCase: GetMealCuisinesUseCase
)
