package org.thechance.service_restaurant.api.usecases

import org.koin.core.annotation.Single

@Single
data class MealUseCasesContainer(
    val addMealUseCase: AddMealUseCase,
    val deleteMealUseCase: DeleteMealUseCase,
    val getMealByIdUseCase: GetMealByIdUseCase,
    val getMealsUseCase: GetMealsUseCase,
    val updateMealUseCase: UpdateMealUseCase
)
