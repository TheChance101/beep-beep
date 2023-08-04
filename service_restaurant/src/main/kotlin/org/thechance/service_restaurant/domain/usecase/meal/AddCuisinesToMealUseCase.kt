package org.thechance.service_restaurant.domain.usecase.meal

interface AddCuisinesToMealUseCase {

    suspend operator fun invoke(mealId: String, cuisineIds: List<String>): Boolean

}