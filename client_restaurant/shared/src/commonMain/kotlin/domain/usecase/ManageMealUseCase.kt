package domain.usecase

import domain.entity.Meal
import domain.entity.MealAddition
import domain.gateway.IFakeRemoteGateWay
import domain.gateway.IRemoteCuisineGateway
import domain.gateway.IRemoteMealGateway
import presentation.base.ServerSideException

interface IManageMealUseCase {
    suspend fun addMeal(meal: MealAddition): Boolean
    suspend fun updateMeal(meal: Meal): Boolean
    suspend fun getMeal(mealId: String): Meal
    suspend fun isValidMeal(meal: Meal): Boolean
    suspend fun getAllMeals():List<Meal>
}

class ManageMealUseCase(
    private val remoteMealGateway: IFakeRemoteGateWay,
    private val remoteCuisineGateway: IFakeRemoteGateWay
) : IManageMealUseCase {
    override suspend fun addMeal(meal: MealAddition): Boolean {
        return remoteMealGateway.addMeal(meal as Meal) != null
    }

    override suspend fun updateMeal(meal: Meal): Boolean {
        return remoteMealGateway.updateMeal(meal) != null
    }

    override suspend fun getMeal(mealId: String): Meal {
        val meal = remoteMealGateway.getMealById(mealId) ?: throw ServerSideException()
        val cuisine = remoteCuisineGateway.getCuisinesInMeal(mealId)
        return meal.copy(cuisines = cuisine)
    }

    override suspend fun getAllMeals(): List<Meal> {
        //TODO fetch restaurant id from local.
        return remoteMealGateway.getMealsByRestaurantId("6ab493b4-4b8d-410a-a13e-780346243f3a")
    }
    override suspend fun isValidMeal(meal: Meal): Boolean {
        TODO("Not yet implemented")
    }
}
