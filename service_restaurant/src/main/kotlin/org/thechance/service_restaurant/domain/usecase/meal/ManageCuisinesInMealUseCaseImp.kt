package org.thechance.service_restaurant.domain.usecase.meal

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.gateway.MealGateway

@Single
class ManageCuisinesInMealUseCaseImp(private val mealGateway:MealGateway) : ManageCuisinesInMealUseCase {
    override suspend fun addCuisinesToMeal(mealId: String, cuisineIds: List<String>): Boolean {
       return mealGateway.addCuisinesToMeal(mealId, cuisineIds)
    }

    override suspend fun deleteCuisineFromMeal(mealId: String, cuisineId: String): Boolean {
        return mealGateway.deleteCuisineFromMeal(mealId, cuisineId)
    }

    override suspend fun getMealCuisines(mealId: String): List<Cuisine> {
        return mealGateway.getMealCuisines(mealId)
    }
}