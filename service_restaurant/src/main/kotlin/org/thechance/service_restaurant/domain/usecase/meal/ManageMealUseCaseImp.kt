package org.thechance.service_restaurant.domain.usecase.meal

import io.ktor.server.plugins.*
import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.gateway.MealGateway

@Single
class ManageMealUseCaseImp(private val mealGateway: MealGateway) : ManageMealUseCase {
    override suspend fun addMeal(meal: Meal): Boolean {
        return mealGateway.addMeal(meal)
    }

    override suspend fun deleteMeal(id: String): Boolean {
        return mealGateway.deleteMealById(id)
    }

    override suspend fun getMealById(id: String): Meal {
        return mealGateway.getMealById(id) ?: throw NotFoundException()
    }

    override suspend fun getMeals(page: Int, limit: Int): List<Meal> {
        return mealGateway.getMeals(page, limit)
    }

    override suspend fun updateMeal(meal: Meal): Boolean {
        return mealGateway.updateMeal(meal)
    }
}


