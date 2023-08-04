package org.thechance.service_restaurant.domain.usecase.meal

import org.thechance.service_restaurant.domain.entity.Cuisine

class ManageCuisinesInMealUseCaseImp : ManageCuisinesInMealUseCase {
    override suspend fun addCuisinesToMeal(mealId: String, cuisineIds: List<String>): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCuisineFromMeal(mealId: String, cuisineId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getMealCuisines(mealId: String): List<Cuisine> {
        TODO("Not yet implemented")
    }
}