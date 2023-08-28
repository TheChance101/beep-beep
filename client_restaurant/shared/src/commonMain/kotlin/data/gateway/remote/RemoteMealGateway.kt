package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.model.BaseResponse
import data.remote.model.MealDto
import domain.entity.Meal
import domain.gateway.remote.IRemoteMealGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put


class RemoteMealGateway(client: HttpClient) : IRemoteMealGateway,
    BaseRemoteGateway(client = client) {

    override suspend fun getAllMealsByRestaurantId(restaurantId: String): List<Meal> {
        return tryToExecute<BaseResponse<List<MealDto>>> {
            get("/meal/") {
                header("Accept-Language", "ar")
                header("Country-Code", "EG")
            }
        }.value?.toEntity() ?: throw Exception()
    }

    override suspend fun getMealsByCuisineId(mealId: String): List<Meal> {
        return tryToExecute<BaseResponse<List<MealDto>>> {
            get("/cuisine/") {
                header("Accept-Language", "ar")
                header("Country-Code", "EG")
            }
        }.value?.toEntity() ?: throw Exception()
    }

    override suspend fun addMeal(meal: Meal): Meal {
        return tryToExecute<BaseResponse<MealDto>> {
            post("/meal/") {
                header("Accept-Language", "ar")
                header("Country-Code", "EG")
            }
        }.value?.toEntity() ?: throw Exception()
    }

    override suspend fun updateMeal(meal: Meal): Meal {
        return tryToExecute<BaseResponse<MealDto>> {
            put("/meal/") {
                header("Accept-Language", "ar")
                header("Country-Code", "EG")
            }
        }.value?.toEntity() ?: throw Exception()
    }
}