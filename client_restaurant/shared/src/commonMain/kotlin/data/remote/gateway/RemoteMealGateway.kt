package data.remote.gateway

import domain.entity.Meal
import domain.gateway.IRemoteMealGateway
import io.ktor.client.HttpClient


class RemoteMealGateway (private val client: HttpClient) : IRemoteMealGateway {

     override suspend fun getMealsByRestaurantId(restaurantId: String): List<Meal> {
        return emptyList()
    }

    override suspend fun getMealById(mealId: String): Meal? {
        return getMealsByRestaurantId("ef77d90").find { it.id == mealId }
    }

    override suspend fun addMeal(meal: Meal): Meal {
        return meal
    }

    override suspend fun updateMeal(meal: Meal): Meal {
        return meal
    }
}