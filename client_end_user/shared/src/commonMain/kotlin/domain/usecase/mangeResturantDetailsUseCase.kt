package domain.usecase

import domain.entity.Meal
import domain.entity.Restaurant
import domain.gateway.IRestaurantGateway

interface IMangeRestaurantUseCase {
    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun getMealById(mealId: String): Meal
}

class MangeRestaurantUseCase(
    private val restaurantRemoteGateway: IRestaurantGateway,
) : IMangeRestaurantUseCase {
    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        return restaurantRemoteGateway.getRestaurantDetails(restaurantId)
    }

    override suspend fun getMealById(mealId: String): Meal {
        return restaurantRemoteGateway.getMealById(mealId)
    }
}