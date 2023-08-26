package domain.usecase

import data.remote.gateway.FakeRemoteGateWay
import domain.entity.Meal
import domain.entity.MealAddition
import domain.gateway.IRemoteGateWay
import presentation.base.ServerSideException

interface IManageMealUseCase {
    suspend fun addMeal(meal: MealAddition): Boolean
    suspend fun updateMeal(meal: Meal): Boolean
    suspend fun getMeal(mealId: String): Meal
    suspend fun isValidMeal(meal: Meal): Boolean
    suspend fun getAllMeals():List<Meal>
}

class ManageMealUseCase(private val remoteGateWay: FakeRemoteGateWay) : IManageMealUseCase {
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

    override suspend fun getAllMeals(): List<Meal> {
        //TODO fetch restaurant id from local.
        return remoteGateWay.getMealsByRestaurantId("6ab493b4-4b8d-410a-a13e-780346243f3a")
    }
    override suspend fun isValidMeal(meal: Meal): Boolean {
        TODO("Not yet implemented")
    }
}
