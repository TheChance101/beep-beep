package domain.usecase

import domain.entity.Meal
import domain.entity.MealAddition
import domain.gateway.remote.ICuisineRemoteGateway
import domain.gateway.remote.IMealRemoteGateway

interface IManageMealUseCase {
    suspend fun addMeal(meal: MealAddition): Boolean
    suspend fun updateMeal(meal: Meal): Boolean
    suspend fun getMealById(mealId: String): Meal
    suspend fun isValidMeal(meal: Meal): Boolean
    suspend fun getAllMeals(
        restaurantId: String,
        page: Int,
        limit: Int
    ): List<Meal>
}

class ManageMealUseCase(
    private val mealRemoteGateway: IMealRemoteGateway,
    private val cuisineRemoteGateway: ICuisineRemoteGateway
) : IManageMealUseCase {
    override suspend fun addMeal(meal: MealAddition): Boolean {
        return mealRemoteGateway.addMeal(meal as Meal) != null
    }

    override suspend fun updateMeal(meal: Meal): Boolean {
        return mealRemoteGateway.updateMeal(meal) != null
    }

    override suspend fun getMealById(mealId: String): Meal {
        val meal = mealRemoteGateway.getMealById(mealId)
        val cuisine = cuisineRemoteGateway.getCuisines()
        return meal.copy(cuisines = cuisine)
    }

    override suspend fun getAllMeals(
        restaurantId: String,
        page: Int,
        limit: Int
    ): List<Meal> {
        //TODO fetch restaurant id from local.
        return mealRemoteGateway.getAllMealsByRestaurantId(restaurantId, page, limit)
    }

    override suspend fun isValidMeal(meal: Meal): Boolean {
        TODO("Not yet implemented")
    }
}
