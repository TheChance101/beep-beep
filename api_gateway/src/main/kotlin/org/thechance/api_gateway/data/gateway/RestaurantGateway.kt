package org.thechance.api_gateway.data.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.CuisineResource
import org.thechance.api_gateway.data.model.restaurant.MealResource
import org.thechance.api_gateway.data.model.restaurant.RestaurantResource
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.model.Order
import org.thechance.api_gateway.endpoints.model.RestaurantRequestPermission
import org.thechance.api_gateway.util.APIs
import java.util.*


@Single(binds = [IRestaurantGateway::class])
class RestaurantGateway(
    client: HttpClient,
    attributes: Attributes,
    private val errorHandler: ErrorHandler
) : BaseGateway(client = client, attributes = attributes), IRestaurantGateway {

    override suspend fun createRequestPermission(
        restaurantName: String, ownerEmail: String, cause: String, locale: Locale
    ): RestaurantRequestPermission {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            submitForm("/restaurant-permission-request",
                formParameters = parameters {
                    append("restaurantName", restaurantName)
                    append("ownerEmail", ownerEmail)
                    append("cause", cause)
                }
            )
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

    override suspend fun getRestaurantInfo(locale: Locale, restaurantId: String): RestaurantResource {
        return tryToExecute<RestaurantResource>(
            APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/restaurant/$restaurantId")
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun updateRestaurantForAdmin(
        restaurant: RestaurantResource,
        permissions: List<Int>,
        locale: Locale
    ): RestaurantResource {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            put("/restaurant") {
                body = Json.encodeToString(
                    RestaurantResource.serializer(),
                    RestaurantResource(
                        id = restaurant.id,
                        name = restaurant.name,
                        phone = restaurant.phone,
                        description = restaurant.description,
                        openingTime = restaurant.openingTime,
                        closingTime = restaurant.closingTime
                    )
                )
            }

        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun updateRestaurant(
        locale: Locale,
        restaurant: RestaurantResource,
        permissions: List<Int>
    ): RestaurantResource {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            put("/restaurant/details") {
                body = Json.encodeToString(
                    RestaurantResource.serializer(),
                    restaurant
                )
            }
        }
    }

    override suspend fun getRestaurants(page: Int, limit: Int, locale: Locale) = tryToExecute<List<RestaurantResource>>(
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

    override suspend fun getRestaurantsByOwnerId(ownerId: String, locale: Locale): List<RestaurantResource> {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/restaurants/$ownerId")
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

    @OptIn(InternalAPI::class)
    override suspend fun addMeal(
        meal: MealResource,
        locale: Locale
    ): MealResource {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            post("/meal") {
                body = Json.encodeToString(
                    MealResource.serializer(),
                    MealResource(
                        restaurantId = meal.restaurantId,
                        name = meal.name,
                        description = meal.description,
                        price = meal.price,
                        cuisines = meal.cuisines
                    )
                )
            }
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun updateMeal(meal: MealResource, locale: Locale): MealResource {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            put("/meal") {
                body = Json.encodeToString(
                    MealResource.serializer(),
                    MealResource(
                        restaurantId = meal.restaurantId,
                        name = meal.name,
                        description = meal.description,
                        price = meal.price,
                        cuisines = meal.cuisines
                    )
                )
            }
        }
    }


    override suspend fun getMealsByRestaurantId(
        restaurantId: String,
        page: Int,
        limit: Int,
        locale: Locale
    ): List<MealResource> {
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

    override suspend fun getMealsByCuisineId(cuisineId: String, locale: Locale): List<MealResource> {
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
    override suspend fun addCuisine(name: String, locale: Locale): CuisineResource {
        return tryToExecute<CuisineResource>(
            APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            post("/cuisine") {
                body = Json.encodeToString(CuisineResource.serializer(), CuisineResource(name = name))
            }
        }
    }


    override suspend fun getCuisines(locale: Locale): List<CuisineResource> {
        return tryToExecute<List<CuisineResource>>(
            APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/cuisines")
        }
    }

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
        restaurantId: String,
        daysBack: Int,
        locale: Locale
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
        restaurantId: String,
        daysBack: Int,
        locale: Locale
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
}