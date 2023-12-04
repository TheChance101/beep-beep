package data.gateway.remote

import data.remote.mapper.toDto
import data.remote.mapper.toDtoUpdate
import data.remote.mapper.toEntity
import data.remote.mapper.toCuisinesWithMealsEntity
import data.remote.model.BaseResponse
import data.remote.model.CuisineWithMealsDto
import data.remote.model.MealDto
import data.remote.model.MealModificationDto
import data.remote.model.PaginationResponse
import domain.entity.CuisineWithMeals
import domain.entity.Meal
import domain.entity.MealModification
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
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json

class MealRemoteGateway(client: HttpClient) : IMealRemoteGateway,
    BaseRemoteGateway(client = client) {

    override suspend fun getAllMealsByRestaurantId(
        restaurantId: String, page: Int, limit: Int,
    ): List<Meal> {
        return tryToExecute<BaseResponse<PaginationResponse<MealDto>>> {
            get("restaurant/$restaurantId/meals") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }.value?.items?.map { it.toEntity() } ?: emptyList()
    }

    override suspend fun getMealsByCuisineId(
        cuisineId: String, page: Int, limit: Int,
    ): List<Meal> {
        return tryToExecute<BaseResponse<PaginationResponse<MealDto>>> {
            get("/cuisine/$cuisineId/meals") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }.value?.items?.map { it.toEntity() } ?: emptyList()
    }

    override suspend fun getMealById(mealId: String): Meal {
        return tryToExecute<BaseResponse<MealDto>> {
            get("meal/$mealId")
        }.value?.toEntity() ?: throw Exception("meal not found")
    }

    override suspend fun getCuisinesWithMealsInRestaurant(restaurantId: String): List<CuisineWithMeals> {
        return tryToExecute<BaseResponse<List<CuisineWithMealsDto>>> {
            get("/restaurant/${restaurantId}/cuisineMeals")
        }.value?.toCuisinesWithMealsEntity() ?: throw Exception("Cuisines not found")
    }

    override suspend fun addMeal(meal: MealModification): MealModification {
        return tryToExecute<BaseResponse<MealModificationDto>> {
            post("/meal") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            header("Content-Type", ContentType.MultiPart.FormData.toString())
                            append(
                                "data",
                                Json.encodeToString(MealModificationDto.serializer(), meal.toDto())
                            )
                            append("image", meal.image, Headers.build {
                                append(HttpHeaders.ContentType, "image/png/jpg/jpeg")
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "form-data; name=image; filename=image.png"
                                )
                            }
                            )
                        }
                    )
                )
            }
        }.value?.toEntity() ?: MealModificationDto().toEntity()
    }

    override suspend fun updateMeal(meal: MealModification): MealModification {
        return tryToExecute<BaseResponse<MealModificationDto>> {
            println(Json.encodeToString(MealModificationDto.serializer(), meal.toDtoUpdate()))
            println("imageeee ${meal.image.size}")
            put("/meal") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            header("Content-Type", ContentType.MultiPart.FormData.toString())
                            append(
                                "data",
                                Json.encodeToString(
                                    MealModificationDto.serializer(),
                                    meal.toDtoUpdate()
                                )
                            )
                            if (meal.image.isNotEmpty()) {
                                append(
                                    "image", meal.image, Headers.build {
                                        append(HttpHeaders.ContentType, "image/png/jpg/jpeg")
                                        append(
                                            HttpHeaders.ContentDisposition,
                                            "form-data; name=image; filename=image.png"
                                        )
                                    }
                                )
                            }
                        }
                    )
                )
            }
        }.value?.toEntity() ?: MealModificationDto().toEntity()
    }
}
