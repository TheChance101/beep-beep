package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.model.BaseResponse
import data.remote.model.MealDto
import domain.entity.Meal
import domain.gateway.remote.IMealRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import presentation.mealManagement.MealDetails


class MealRemoteGateway(client: HttpClient) : IMealRemoteGateway,
    BaseRemoteGateway(client = client) {

    override suspend fun getAllMealsByRestaurantId(
        restaurantId: String,
        page: Int,
        limit: Int,
    ): List<Meal>{
        return tryToExecute<BaseResponse<List<MealDto>>> {
            get("restaurant/$restaurantId/meals") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }.value?.toEntity() ?: emptyList()
    }

    override suspend fun getMealsByCuisineId(cuisineId: String): List<Meal> {
        return tryToExecute<BaseResponse<List<MealDto>>> {
            get("/cuisine/$cuisineId/meals")
        }.value?.toEntity() ?: emptyList()
    }

    override suspend fun getMealById(mealId: String): MealDetails {
        return tryToExecute<BaseResponse<MealDetails>> {
            get("meal/$mealId")
        }.value ?: throw Exception("meal not found")
    }

    override suspend fun addMeal(meal: Meal): Meal {
        return meal
    }

    override suspend fun updateMeal(meal: Meal): Meal {
        return meal
    }
}