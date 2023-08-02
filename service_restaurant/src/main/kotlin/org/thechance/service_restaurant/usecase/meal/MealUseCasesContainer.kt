package org.thechance.service_restaurant.usecase.meal

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.usecase.meal.*

@Single
data class MealUseCasesContainer(
    val addMealUseCase: AddMealUseCase,
    val deleteMealUseCase: DeleteMealUseCase,
    val getMealByIdUseCase: GetMealByIdUseCase,
    val getMealsUseCase: GetMealsUseCase,
    val updateMealUseCase: UpdateMealUseCase
)
