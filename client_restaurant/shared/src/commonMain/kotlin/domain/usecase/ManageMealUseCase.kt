package domain.usecase

import domain.entity.Meal
import domain.entity.MealAddition
import domain.gateway.IRemoteGateWay
import presentation.base.ServerSideException

interface IManageMealUseCase {
    suspend fun addMeal(meal: MealAddition): Boolean
    suspend fun updateMeal(meal: Meal): Boolean
    suspend fun getMeal(mealId: String): Meal

    suspend fun isValidMeal(meal: Meal): Boolean

}

class ManageMealUseCase(private val remoteGateWay: IRemoteGateWay) : IManageMealUseCase {
    override suspend fun addMeal(meal: MealAddition): Boolean {
        return remoteGateWay.addMeal(meal as Meal) != null
    }

    override suspend fun updateMeal(meal: Meal): Boolean {
        return remoteGateWay.updateMeal(meal) != null
    }

    override suspend fun getMeal(mealId: String): Meal {
        val meal = remoteGateWay.getMealById(mealId) ?: throw ServerSideException()
        val cuisine = remoteGateWay.getCuisinesInMeal(mealId)
        return meal.copy(cuisines = cuisine)
    }

    override suspend fun isValidMeal(meal: Meal): Boolean {
        TODO("Not yet implemented")
    }
}
