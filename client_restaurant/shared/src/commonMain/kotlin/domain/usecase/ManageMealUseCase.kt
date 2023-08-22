package domain.usecase

import domain.entity.Cuisine
import domain.entity.Meal
import domain.gateway.IRemoteGateWay
import presentation.base.ServerSideException

interface IManageMealUseCase {
    suspend fun addMeal(): Boolean

    suspend fun getMeal(mealId: String): Meal

    suspend fun updateMeal(meal: Meal)

    suspend fun getCuisines(): List<Cuisine>

}

class ManageMealUseCase(private val remoteGateWay: IRemoteGateWay) : IManageMealUseCase {
    override suspend fun addMeal(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getMeal(mealId: String): Meal {
        val meal = remoteGateWay.getMealById(mealId) ?: throw ServerSideException()
        val cuisine = remoteGateWay.getCuisinesInMeal(mealId)
        return meal.copy(cuisines = cuisine)
    }

    override suspend fun updateMeal(meal: Meal) {
        TODO("Not yet implemented")
    }

    override suspend fun getCuisines(): List<Cuisine> {
        return remoteGateWay.getCuisines()
    }


}
