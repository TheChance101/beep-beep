package data.gateway.remote

import data.remote.mapper.toDto
import data.remote.mapper.toEntity
import data.remote.model.BaseResponse
import data.remote.model.MealDto
import domain.entity.Meal
import domain.gateway.remote.IMealRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody


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

    override suspend fun getMealById(mealId: String): Meal {
        return tryToExecute<BaseResponse<Meal>> {
            get("meal/$mealId")
        }.value ?: throw Exception("meal not found")
    }

    override suspend fun addMeal(meal: Meal): Boolean {
        return tryToExecute<BaseResponse<Boolean>> {
            post("/meal") { setBody(meal.toDto()) }
        }.value ?: false
    }

    override suspend fun updateMeal(meal: Meal): Boolean {
        return tryToExecute<BaseResponse<Boolean>> {
            put("/meal") { setBody(meal.toDto()) }
        }.value ?: false
    }
}

