package data.gateway.remote

import domain.entity.Meal
import domain.gateway.remote.IRemoteMealGateway
import io.ktor.client.HttpClient


class RemoteMealGateway (private val client: HttpClient) : IRemoteMealGateway {

     override suspend fun getAllMealsByRestaurantId(restaurantId: String): List<Meal> {
        return emptyList()
    }

    override suspend fun getMealsByCuisineId(mealId: String): List<Meal> {
        return emptyList()
    }

    override suspend fun addMeal(meal: Meal): Meal {
        return meal
    }

    override suspend fun updateMeal(meal: Meal): Meal {
        return meal
    }
}