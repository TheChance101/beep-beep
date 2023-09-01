package org.thechance.api_gateway.data.service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.PaginationResponse
import org.thechance.api_gateway.data.model.restaurant.CuisineDto
import org.thechance.api_gateway.data.model.restaurant.OrderDto
import org.thechance.api_gateway.data.model.restaurant.MealDto
import org.thechance.api_gateway.data.model.restaurant.MealDetailsDto
import org.thechance.api_gateway.data.model.restaurant.RestaurantDto
import org.thechance.api_gateway.data.model.restaurant.RestaurantRequestPermissionDto
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.data.utils.tryToExecute
import org.thechance.api_gateway.data.utils.tryToExecuteFromWebSocket
import org.thechance.api_gateway.util.APIs
import java.util.*


@Single
class RestaurantService(
    private val client: HttpClient,
    private val attributes: Attributes,
    private val errorHandler: ErrorHandler
) {

    @OptIn(InternalAPI::class)
    suspend fun createRequestPermission(
        requestedForm: RestaurantRequestPermissionDto, locale: Locale
    ): RestaurantRequestPermissionDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            post("/restaurant-permission-request") {
                body = Json.encodeToString(RestaurantRequestPermissionDto.serializer(), requestedForm)
            }
        }
    }

    suspend fun getAllRequestPermission(locale: Locale): List<RestaurantRequestPermissionDto> {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/restaurant-permission-request")
        }
    }

    //region Restaurant
    suspend fun getRestaurantInfo(locale: Locale, restaurantId: String): RestaurantDto {
        return client.tryToExecute(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/restaurant/$restaurantId")
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun updateRestaurant(restaurantDto: RestaurantDto, isAdmin: Boolean, locale: Locale): RestaurantDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            val url = if (isAdmin) {
                "/restaurant"
            } else {
                "/restaurant/details"
            }
            put(url) {
                body = Json.encodeToString(RestaurantDto.serializer(), restaurantDto)
            }

        }
    }

    suspend fun getRestaurants(page: Int, limit: Int, locale: Locale) =
        client.tryToExecute<PaginationResponse<RestaurantDto>>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/restaurants") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }

    suspend fun getRestaurantsByOwnerId(ownerId: String, locale: Locale): List<RestaurantDto> {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) { get("/restaurants/$ownerId") }
    }

    @OptIn(InternalAPI::class)
    suspend fun addRestaurant(restaurantDto: RestaurantDto, locale: Locale): RestaurantDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            post("/restaurant") {
                body = Json.encodeToString(RestaurantDto.serializer(), restaurantDto)
            }
        }
    }

    suspend fun deleteRestaurant(restaurantId: String, locale: Locale): Boolean {
        return client.tryToExecute<Boolean>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorHandler.getLocalizedErrorMessage(it, locale) }
        ) {
            delete("/restaurant/$restaurantId")
        }
    }

    //endregion

    //region meal
    suspend fun getMeal(mealId: String, locale: Locale): MealDetailsDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) { get("meal/$mealId") }
    }

    suspend fun getMealsByRestaurantId(
        restaurantId: String, page: Int, limit: Int, locale: Locale
    ): List<MealDto> {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("restaurant/$restaurantId/meals") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }
    }

    suspend fun getMealsByCuisineId(cuisineId: String, locale: Locale): List<MealDto> {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/cuisine/$cuisineId/meals")
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun addMeal(mealDto: MealDto, locale: Locale): MealDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            post("/meal") {
                body = Json.encodeToString(MealDto.serializer(), mealDto)
            }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun updateMeal(mealDto: MealDto, locale: Locale): MealDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            put("/meal") { body = Json.encodeToString(MealDto.serializer(), mealDto) }
        }
    }

    //endregion

    //region cuisine
    @OptIn(InternalAPI::class)
    suspend fun addCuisine(name: String, locale: Locale): CuisineDto {
        return client.tryToExecute<CuisineDto>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            post("/cuisine") {
                body = Json.encodeToString(CuisineDto.serializer(), CuisineDto(name = name))
            }
        }
    }

    suspend fun getCuisines(locale: Locale): List<CuisineDto> {
        return client.tryToExecute<List<CuisineDto>>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/cuisines")
        }
    }
    //endregion

    //region order
    @OptIn(InternalAPI::class)
    suspend fun updateOrderStatus(orderId: String, status: Int, locale: Locale): OrderDto {
        return client.tryToExecute<OrderDto>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            post("/order/$orderId/status") {
                body = Json.encodeToString(status.toString())
            }
        }
    }

    suspend fun getOrdersHistory(restaurantId: String, page: Int, limit: Int, locale: Locale): List<OrderDto> {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/order/history/$restaurantId?page=$page&limit=$limit")
        }
    }

    suspend fun getOrdersCountByDaysBefore(
        restaurantId: String, daysBack: Int, locale: Locale
    ): List<Map<Int, Int>> {
        return client.tryToExecute<List<Map<Int, Int>>>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/order/count-by-days-back?restaurantId=$restaurantId&&daysBack=$daysBack")
        }
    }

    suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String, daysBack: Int, locale: Locale
    ): List<Map<Int, Double>> {
        return client.tryToExecute<List<Map<Int, Double>>>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/order/revenue-by-days-back?restaurantId=$restaurantId&&daysBack=$daysBack")
        }
    }

    suspend fun restaurantOrders(restaurantId: String, locale: Locale): Flow<OrderDto> {
        return client.tryToExecuteFromWebSocket<OrderDto>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            path = "/order/restaurant/$restaurantId"
        )
    }

    suspend fun getActiveOrders(restaurantId: String, locale: Locale): List<OrderDto> {
        return client.tryToExecute<List<OrderDto>>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(
                    errorCodes,
                    locale
                )
            }
        ) {
            get("/order/$restaurantId/orders")
        }
    }
    //endregion
}