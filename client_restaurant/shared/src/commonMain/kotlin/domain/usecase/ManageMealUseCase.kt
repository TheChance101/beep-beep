package domain.usecase

import data.remote.model.MealModificationDto
import domain.entity.Meal
import domain.entity.MealModification
import domain.gateway.remote.ICuisineRemoteGateway
import domain.gateway.remote.IMealRemoteGateway

interface IManageMealUseCase {
    suspend fun addMeal(meal: MealModification): MealModification
    suspend fun updateMeal(meal: MealModification): MealModification
    suspend fun getMealById(mealId: String): Meal
    suspend fun isValidMeal(meal: Meal): Boolean
    suspend fun getAllMeals(restaurantId: String, page: Int, limit: Int): List<Meal>
    suspend fun getMealsByCuisineId(cuisineId: String, page: Int, limit: Int): List<Meal>
}

class ManageMealUseCase(
    private val mealRemoteGateway: IMealRemoteGateway,
    private val cuisineRemoteGateway: ICuisineRemoteGateway
) : IManageMealUseCase {
    override suspend fun addMeal(meal: MealModification): MealModification {
        return mealRemoteGateway.addMeal(meal)
    }

    override suspend fun updateMeal(meal: MealModification): MealModification {
        return mealRemoteGateway.updateMeal(meal)
    }

    override suspend fun getMealById(mealId: String): Meal {
        val meal = mealRemoteGateway.getMealById(mealId)
        val cuisine = cuisineRemoteGateway.getCuisines()
        return meal.copy(cuisines = cuisine)
    }

    override suspend fun getAllMeals(restaurantId: String, page: Int, limit: Int): List<Meal> {
        return mealRemoteGateway.getAllMealsByRestaurantId(restaurantId,page,limit)
    }

    override suspend fun getMealsByCuisineId(cuisineId: String, page: Int, limit: Int): List<Meal> {
        return mealRemoteGateway.getMealsByCuisineId(cuisineId, page, limit)
    }

    override suspend fun isValidMeal(meal: Meal): Boolean {
        TODO("Not yet implemented")
    }
}
