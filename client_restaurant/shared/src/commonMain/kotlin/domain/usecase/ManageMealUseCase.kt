package domain.usecase

import domain.entity.CuisineWithMeals
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
    suspend fun getCuisinesWithMealsInRestaurant(restaurantId: String): List<CuisineWithMeals>
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
        return mealRemoteGateway.getMealById(mealId)
    }

    override suspend fun getAllMeals(restaurantId: String, page: Int, limit: Int): List<Meal> {
        return mealRemoteGateway.getAllMealsByRestaurantId(restaurantId, page, limit)
    }

    override suspend fun getCuisinesWithMealsInRestaurant(restaurantId: String)
            : List<CuisineWithMeals> {
        return mealRemoteGateway.getCuisinesWithMealsInRestaurant(restaurantId)
    }

    override suspend fun getMealsByCuisineId(cuisineId: String, page: Int, limit: Int): List<Meal> {
        return mealRemoteGateway.getMealsByCuisineId(cuisineId, page, limit)
    }

    override suspend fun isValidMeal(meal: Meal): Boolean {
        TODO("Not yet implemented")
    }
}
