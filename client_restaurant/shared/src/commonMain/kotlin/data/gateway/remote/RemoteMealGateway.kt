package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.model.BaseResponse
import data.remote.model.MealDto
import domain.entity.Meal
import domain.gateway.remote.IRemoteMealGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.get


class RemoteMealGateway(client: HttpClient) : IRemoteMealGateway,
    BaseRemoteGateway(client = client) {

    override suspend fun getAllMealsByRestaurantId(restaurantId: String): List<Meal>? {
        return tryToExecute<BaseResponse<List<MealDto>>> {
            get("/meals/$restaurantId")
        }.value?.toEntity()
    }

    override suspend fun getMealsByCuisineId(mealId: String): List<Meal>? {
        return tryToExecute<BaseResponse<List<MealDto>>> {
            get("/meals/$mealId")
            //todo add pagination
        }.value?.toEntity()
    }

    override suspend fun addMeal(meal: Meal): Meal {
        return meal
    }

    override suspend fun updateMeal(meal: Meal): Meal {
        return meal
    }
}