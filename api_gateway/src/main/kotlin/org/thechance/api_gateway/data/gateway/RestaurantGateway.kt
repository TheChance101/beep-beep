package org.thechance.api_gateway.data.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.BasePaginationResponse
import org.thechance.api_gateway.data.model.Cuisine
import org.thechance.api_gateway.data.model.Order
import org.thechance.api_gateway.data.model.restaurant.Meal
import org.thechance.api_gateway.data.model.restaurant.MealDetails
import org.thechance.api_gateway.data.model.restaurant.Restaurant
import org.thechance.api_gateway.data.model.restaurant.RestaurantRequestPermission
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.util.APIs
import java.util.*


@Single(binds = [IRestaurantGateway::class])
class RestaurantGateway(
    client: HttpClient,
    attributes: Attributes,
    private val errorHandler: ErrorHandler
) : BaseGateway(client = client, attributes = attributes), IRestaurantGateway {

    @OptIn(InternalAPI::class)
    override suspend fun createRequestPermission(
        requestedForm: RestaurantRequestPermission, locale: Locale
    ): RestaurantRequestPermission {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            post("/restaurant-permission-request") {
                body = Json.encodeToString(RestaurantRequestPermission.serializer(), requestedForm)
            }
        }
    }

    override suspend fun getAllRequestPermission(locale: Locale): List<RestaurantRequestPermission> {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/restaurant-permission-request")
        }
    }

    //region Restaurant
    override suspend fun getRestaurantInfo(locale: Locale, restaurantId: String): Restaurant {
        return tryToExecute(
            APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/restaurant/$restaurantId")
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun updateRestaurant(restaurant: Restaurant, isAdmin: Boolean, locale: Locale): Restaurant {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
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
                body = Json.encodeToString(Restaurant.serializer(), restaurant)
            }

        }
    }

    override suspend fun getRestaurants(page: Int, limit: Int, locale: Locale) =
        tryToExecute<BasePaginationResponse<Restaurant>>(
            APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/restaurants") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }

    override suspend fun getRestaurantsByOwnerId(ownerId: String, locale: Locale): List<Restaurant> {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) { get("/restaurants/$ownerId") }
    }

    @OptIn(InternalAPI::class)
    override suspend fun addRestaurant(restaurant: Restaurant, locale: Locale): Restaurant {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            post("/restaurant") {
                body = Json.encodeToString(Restaurant.serializer(), restaurant)
            }
        }
    }

    override suspend fun deleteRestaurant(restaurantId: String, locale: Locale): Boolean {
        return tryToExecute<Boolean>(
            APIs.RESTAURANT_API,
            setErrorMessage = { errorHandler.getLocalizedErrorMessage(it, locale) }
        ) {
            delete("/restaurant/$restaurantId")
        }
    }

    //endregion

    //region meal
    override suspend fun getMeal(mealId: String, locale: Locale): MealDetails {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) { get("meal/$mealId") }
    }

    override suspend fun getMealsByRestaurantId(
        restaurantId: String, page: Int, limit: Int, locale: Locale
    ): List<Meal> {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
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

    override suspend fun getMealsByCuisineId(cuisineId: String, locale: Locale): List<Meal> {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/cuisine/$cuisineId/meals")
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun addMeal(meal: Meal, locale: Locale): Meal {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            post("/meal") {
                body = Json.encodeToString(Meal.serializer(), meal)
            }
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun updateMeal(meal: Meal, locale: Locale): Meal {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            put("/meal") { body = Json.encodeToString(Meal.serializer(), meal) }
        }
    }

    //endregion

    //region cuisine
    @OptIn(InternalAPI::class)
    override suspend fun addCuisine(name: String, locale: Locale): Cuisine {
        return tryToExecute<Cuisine>(
            APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            post("/cuisine") {
                body = Json.encodeToString(Cuisine.serializer(), Cuisine(name = name))
            }
        }
    }

    override suspend fun getCuisines(locale: Locale): List<Cuisine> {
        return tryToExecute<List<Cuisine>>(
            APIs.RESTAURANT_API,
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
    override suspend fun updateOrderStatus(orderId: String, status: Int, locale: Locale): Order {
        return tryToExecute<Order>(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            post("/order/$orderId/status") {
                body = Json.encodeToString(status.toString())
            }
        }
    }

    override suspend fun getOrdersHistory(restaurantId: String, page: Int, limit: Int, locale: Locale): List<Order> {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/order/history/$restaurantId?page=$page&limit=$limit")
        }
    }

    override suspend fun getOrdersCountByDaysBefore(
        restaurantId: String, daysBack: Int, locale: Locale
    ): List<Map<Int, Int>> {
        return tryToExecute<List<Map<Int, Int>>>(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/order/count-by-days-back?restaurantId=$restaurantId&&daysBack=$daysBack")
        }
    }

    override suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String, daysBack: Int, locale: Locale
    ): List<Map<Int, Double>> {
        return tryToExecute<List<Map<Int, Double>>>(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/order/revenue-by-days-back?restaurantId=$restaurantId&&daysBack=$daysBack")
        }
    }

    override suspend fun restaurantOrders(restaurantId: String, locale: Locale): Flow<Order> {
        return tryToExecuteFromWebSocket<Order>(api = APIs.RESTAURANT_API, path = "/order/restaurant/$restaurantId")
    }

    override suspend fun getActiveOrders(restaurantId: String, locale: Locale): List<Order> {
        return tryToExecute<List<Order>>(
            api = APIs.RESTAURANT_API,
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