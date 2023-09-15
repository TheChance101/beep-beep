package org.thechance.api_gateway.data.service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.utils.EmptyContent.status
import io.ktor.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.PaginationResponse
import org.thechance.api_gateway.data.model.restaurant.*
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.data.utils.tryToExecute
import org.thechance.api_gateway.data.utils.tryToExecuteFromWebSocket
import org.thechance.api_gateway.util.APIs

@Single
class RestaurantService(
    private val client: HttpClient,
    private val attributes: Attributes,
    private val errorHandler: ErrorHandler,
) {
    @OptIn(InternalAPI::class)
    suspend fun createRequestPermission(
        requestedForm: RestaurantRequestPermissionDto, languageCode: String
    ): RestaurantRequestPermissionDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            post("/restaurant-permission-request") {
                body = Json.encodeToString(RestaurantRequestPermissionDto.serializer(), requestedForm)
            }
        }
    }

    suspend fun getAllRequestPermission(languageCode: String): List<RestaurantRequestPermissionDto> {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            get("/restaurant-permission-request")
        }
    }

    //region Restaurant
    suspend fun getRestaurantInfo(languageCode: String, restaurantId: String): RestaurantDto {
        return client.tryToExecute(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            get("/restaurant/$restaurantId")
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun updateRestaurant(restaurantDto: RestaurantDto, isAdmin: Boolean, languageCode: String): RestaurantDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            val url = if (isAdmin) {
                "/restaurant"
            } else {
                "/restaurant/details"
            }
            put(url) {
                body = Json.encodeToString(RestaurantDto.serializer(), restaurantDto)
                body = Json.encodeToString(status.toString())
            }

        }
    }

    suspend fun getRestaurants(page: Int, limit: Int, languageCode: String) =
        client.tryToExecute<PaginationResponse<RestaurantDto>>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            get("/restaurants") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }

    @OptIn(InternalAPI::class)
    suspend fun getRestaurants(restaurantIds: List<String>, languageCode: String) =
        client.tryToExecute<List<RestaurantDto>>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            post("/restaurants") {
                body = Json.encodeToString(restaurantIds)
            }
        }


    suspend fun getRestaurantsByOwnerId(ownerId: String, languageCode: String): List<RestaurantDto> {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) { get("/restaurants/$ownerId") }
    }

    @OptIn(InternalAPI::class)
    suspend fun addRestaurant(restaurant: RestaurantDto, languageCode: String): RestaurantRequestDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            post("/restaurant") {
                body = Json.encodeToString(RestaurantDto.serializer(), restaurant)
            }
        }
    }

    suspend fun deleteRestaurant(restaurantId: String, languageCode: String): Boolean {
        return client.tryToExecute<Boolean>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorHandler.getLocalizedErrorMessage(it, languageCode) }
        ) {
            delete("/restaurant/$restaurantId")
        }
    }

    //endregion

    //region meal
    suspend fun getMeal(mealId: String, languageCode: String): MealDetailsDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) { get("meal/$mealId") }
    }

    suspend fun getMealsByRestaurantId(
        restaurantId: String, page: Int, limit: Int, languageCode: String
    ): List<MealDto> {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            get("restaurant/$restaurantId/meals") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }
    }

    suspend fun getMealsByCuisineId(cuisineId: String, languageCode: String): List<MealDto> {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            get("/cuisine/$cuisineId/meals")
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun addMeal(mealDto: MealDto, languageCode: String): MealDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            post("/meal") {
                body = Json.encodeToString(MealDto.serializer(), mealDto)
            }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun updateMeal(mealDto: MealDto, languageCode: String): MealDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            put("/meal") { body = Json.encodeToString(MealDto.serializer(), mealDto) }
        }
    }

    //endregion

    //region cuisine
    @OptIn(InternalAPI::class)
    suspend fun addCuisine(name: String, languageCode: String): CuisineDto {
        return client.tryToExecute<CuisineDto>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            post("/cuisine") {
                body = Json.encodeToString(CuisineDto.serializer(), CuisineDto(name = name))
            }
        }
    }

    suspend fun getCuisines(languageCode: String): List<CuisineDto> {
        return client.tryToExecute<List<CuisineDto>>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            get("/cuisines")
        }
    }

    suspend fun deleteCuisine(cuisineId: String, languageCode : String): Boolean {
        return client.tryToExecute<Boolean>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorHandler.getLocalizedErrorMessage(it, languageCode ) }
        ) {
            delete("/cuisine/$cuisineId")
        }
    }
    //endregion

    //region order
    @OptIn(InternalAPI::class)
    suspend fun updateOrderStatus(orderId: String, status: Int, languageCode: String): OrderDto {
        return client.tryToExecute<OrderDto>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            post("/order/$orderId/status") {
                body = Json.encodeToString(status.toString())
            }
        }
    }

    suspend fun getOrdersHistory(restaurantId: String, page: Int, limit: Int, languageCode: String): List<OrderDto> {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            get("/order/history/$restaurantId?page=$page&limit=$limit")
        }
    }

    suspend fun getOrdersCountByDaysBefore(
        restaurantId: String, daysBack: Int, languageCode: String
    ): List<Map<Int, Int>> {
        return client.tryToExecute<List<Map<Int, Int>>>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            get("/order/count-by-days-back?restaurantId=$restaurantId&&daysBack=$daysBack")
        }
    }

    suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String, daysBack: Int, languageCode: String
    ): List<Map<Int, Double>> {
        return client.tryToExecute<List<Map<Int, Double>>>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            get("/order/revenue-by-days-back?restaurantId=$restaurantId&&daysBack=$daysBack")
        }
    }

    suspend fun restaurantOrders(restaurantId: String, languageCode: String): Flow<OrderDto> {
        return client.tryToExecuteFromWebSocket<OrderDto>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            path = "/order/restaurant/$restaurantId"
        )
    }

    suspend fun getActiveOrders(restaurantId: String, languageCode: String): List<OrderDto> {
        return client.tryToExecute<List<OrderDto>>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(
                    errorCodes,
                    languageCode
                )
            }
        ) {
            get("/order/$restaurantId/orders")
        }
    }
    //endregion


}