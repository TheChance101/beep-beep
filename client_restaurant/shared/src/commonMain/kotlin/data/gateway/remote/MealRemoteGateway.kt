package data.gateway.remote

import data.remote.mapper.toDto
import data.remote.mapper.toEntity
import data.remote.model.MealModificationDto
import data.remote.model.BaseResponse
import data.remote.model.MealDto
import domain.entity.Meal
import domain.entity.MealAddition
import domain.entity.MealUpdate
import domain.gateway.remote.IMealRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import kotlinx.serialization.json.Json


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
        println("remote : $mealId")
        return tryToExecute<BaseResponse<MealDto>> {
            get("meal/$mealId")
        }.value?.toEntity() ?: throw Exception("meal not found")
    }

    override suspend fun addMeal(meal: MealAddition): Boolean {
        return tryToExecute<BaseResponse<Boolean>> {
            post("/meal") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            header("Content-Type", ContentType.MultiPart.FormData.toString())
                            append("data", Json.encodeToString(MealModificationDto.serializer(), meal.toDto()))
                        }
                    )
                )
            }
        }.value ?: false
    }

    override suspend fun updateMeal(meal: MealUpdate): Boolean {
        return tryToExecute<BaseResponse<Boolean>> {
            put("/meal") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            header("Content-Type", ContentType.MultiPart.FormData.toString())
                            append("data", Json.encodeToString(MealModificationDto.serializer(), meal.toDto()))
                        }
                    )
                )
            }
        }.value == true
    }


}

