package org.thechance.service_restaurant.domain.usecase.meal

import org.thechance.service_restaurant.domain.entity.Meal

interface ManageMealUseCase {

    suspend fun addMeal(meal: Meal): Boolean
    suspend fun deleteMeal(id: String) : Boolean

    suspend fun getMealById(id: String): Meal

    suspend fun getMeals(page : Int , limit : Int): List<Meal>

    suspend fun updateMeal(meal: Meal) : Boolean
}