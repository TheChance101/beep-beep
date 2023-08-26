package data.remote.gateway

import domain.entity.Cuisine
import domain.entity.Meal
import domain.gateway.IRemoteCuisineGateway
import io.ktor.client.HttpClient


class RemoteCuisineGateway(private val client: HttpClient) : IRemoteCuisineGateway {

      override suspend fun getCuisines(): List<Cuisine> {
        return emptyList()
    }

    override suspend fun getCuisinesInMeal(mealId: String): List<Cuisine> {
        return emptyList()
    }

    override suspend fun getCuisine(restaurantId: String): List<Cuisine> {
        return emptyList()
    }

    override suspend fun getMealsByCuisineId(id: String): List<Meal> {
        return emptyList()
    }
    }