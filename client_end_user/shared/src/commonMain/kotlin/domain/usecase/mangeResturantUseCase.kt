package domain.usecase

import domain.entity.Cuisine
import domain.entity.Meal
import domain.entity.Restaurant
import domain.gateway.IRestaurantGateway

interface IMangeRestaurantUseCase {
    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun getMealById(mealId: String): Meal
    suspend fun getCuisines(): List<Cuisine>
    suspend fun getMealsInCuisine(cuisineId: String): List<Meal>
}

class MangeRestaurantUseCase(
    private val restaurantGateway: IRestaurantGateway,
) : IMangeRestaurantUseCase {
    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        return restaurantGateway.getRestaurantDetails(restaurantId)
    }

    override suspend fun getMealById(mealId: String): Meal {
        return restaurantGateway.getMealById(mealId)
    }

    override suspend fun getCuisines(): List<Cuisine> {
        return restaurantGateway.getCuisines()
    }

    override suspend fun getMealsInCuisine(cuisineId: String): List<Meal> {
        return restaurantGateway.getMealsInCuisine(cuisineId)
    }
}
