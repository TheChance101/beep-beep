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
import org.thechance.api_gateway.data.utils.LocalizedMessageException
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.model.Order
import org.thechance.api_gateway.endpoints.model.RestaurantRequestPermission
import org.thechance.api_gateway.util.APIs
import java.util.*

//TODO will delete it after do with permissions
const val ADMIN_PERMISSION = 1
const val RESTAURANT_MANAGER_PERMISSION = 2


@Single(binds = [IRestaurantGateway::class])
class RestaurantGateway(
    client: HttpClient, attributes: Attributes, private val errorHandler: ErrorHandler
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

    override suspend fun getAllRequestPermission(
        permissions: List<Int>,
        locale: Locale
    ): List<RestaurantRequestPermission> {
        if (!permissions.contains(ADMIN_PERMISSION)) {
            throw LocalizedMessageException(errorHandler.getLocalizedErrorMessage(listOf(8000), locale))
        }

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

    override suspend fun getRestaurantsByOwnerId(
        ownerId: String, locale: Locale, permissions: List<Int>
    ): List<RestaurantResource> {
        if (RESTAURANT_MANAGER_PERMISSION in permissions) {
            return tryToExecute(
                api = APIs.RESTAURANT_API,
                setErrorMessage = { errorCodes ->
                    errorHandler.getLocalizedErrorMessage(errorCodes, locale)
                }
            ) {
                get("/restaurants/$ownerId")
            }
        } else {
            throw LocalizedMessageException(errorHandler.getLocalizedErrorMessage(listOf(8000), locale))
        }
    }

    override suspend fun deleteRestaurant(restaurantId: String, permissions: List<Int>, locale: Locale): Boolean {
        return tryToExecute<Boolean>(
            APIs.RESTAURANT_API,
            setErrorMessage = { errorHandler.getLocalizedErrorMessage(it, locale) }
        ) {
            if (!permissions.contains(ADMIN_PERMISSION)) {
                throw LocalizedMessageException(errorHandler.getLocalizedErrorMessage(listOf(8000), locale))
            }
            delete("/restaurant/$restaurantId")
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun addMeal(
        restaurantId: String,
        name: String,
        description: String,
        price: Double,
        cuisines: List<String>,
        permissions: List<Int>,
        locale: Locale
    ): MealResource {
        if (RESTAURANT_MANAGER_PERMISSION in permissions) {
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
                            restaurantId = restaurantId,
                            name = name,
                            description = description,
                            price = price,
                            cuisines = cuisines
                        )
                    )
                }
            }
        } else {
            throw LocalizedMessageException(errorHandler.getLocalizedErrorMessage(listOf(8000), locale))
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun updateMeal(
        restaurantId: String,
        name: String,
        description: String,
        price: Double,
        cuisines: List<String>,
        permissions: List<Int>,
        locale: Locale
    ): MealResource {
        if (RESTAURANT_MANAGER_PERMISSION in permissions) {
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
                            restaurantId = restaurantId,
                            name = name,
                            description = description,
                            price = price,
                            cuisines = cuisines
                        )
                    )
                }
            }
        } else {
            throw LocalizedMessageException(errorHandler.getLocalizedErrorMessage(listOf(8000), locale))
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
            get("restaurants/$restaurantId/meals") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }
    }

    override suspend fun getMealsByCuisineId(
        cuisineId: String,
        locale: Locale
    ): List<MealResource> {
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
    override suspend fun addCuisine(name: String, permissions: List<Int>, locale: Locale): CuisineResource {
        //TODO()  need to change 1
        val ADMIN = 1
        return if (ADMIN in permissions) {
            tryToExecute<CuisineResource>(
                APIs.RESTAURANT_API,
                setErrorMessage = { errorCodes ->
                    errorHandler.getLocalizedErrorMessage(errorCodes, locale)
                }
            ) {
                post("/cuisine") {
                    body = Json.encodeToString(CuisineResource.serializer(), CuisineResource(name = name))
                }
            }
        } else {
            throw LocalizedMessageException(
                errorHandler.getLocalizedErrorMessage(listOf(8000), locale)
            )
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
    override suspend fun updateOrderStatus(
        orderId: String, permissions: List<Int>, status: Int, locale: Locale
    ): Order {
        // todo: implement check permissions logic correctly
        if (!permissions.contains(RESTAURANT_MANAGER_PERMISSION)) {
            throw LocalizedMessageException(errorHandler.getLocalizedErrorMessage(listOf(8000), locale))
        }

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

    override suspend fun getOrdersHistory(
        restaurantId: String, permissions: List<Int>, page: Int, limit: Int, locale: Locale
    ): List<Order> {
        // todo: implement check permissions logic correctly
        if (!permissions.contains(RESTAURANT_MANAGER_PERMISSION)) {
            throw LocalizedMessageException(errorHandler.getLocalizedErrorMessage(listOf(8000), locale))
        }

        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/order/history/$restaurantId?page=$page&limit=$limit")
        }
    }


    override suspend fun restaurantOrders(permissions: List<Int>, restaurantId: String, locale: Locale): Flow<Order> {
        // todo check of permission and handel error
        return tryToExecuteFromWebSocket<Order>(api = APIs.RESTAURANT_API, path = "/order/restaurant/$restaurantId")
    }

    override suspend fun getActiveOrders(permissions: List<Int>, restaurantId: String, locale: Locale): List<Order> {
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

    override suspend fun getOrdersCountByDaysBefore(
        restaurantId: String,
        daysBack: Int,
        permissions: List<Int>,
        locale: Locale
    ): List<Map<Int, Int>> {
        // todo: implement check permissions logic
        return tryToExecute<List<Map<Int, Int>>>(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/order/count-by-days-back?restaurantId=$restaurantId&&daysBack=$daysBack")
        }
    }

}