package org.thechance.api_gateway.data.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.restaurant.RestaurantResource
import org.thechance.api_gateway.data.model.CuisineResource
import org.thechance.api_gateway.data.model.restaurant.MealResource
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.data.utils.LocalizedMessageException
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.model.Order
import org.thechance.api_gateway.endpoints.model.RestaurantRequestPermission
import org.thechance.api_gateway.util.APIs
import java.util.*

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
        // todo: implement check permissions logic correctly
        if (!permissions.contains(1)) {
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
        get("/restaurants"){
            parameter("page", page)
            parameter("limit", limit)
        }
    }
    override suspend fun getRestaurantsByOwnerId(
        ownerId: String,
        locale: Locale,
        permissions: List<Int>
    ): List<RestaurantResource> {

        val RESTAURANT_MANAGER_PERMISSION = 2
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
        val RESTAURANT_MANAGER_PERMISSION = 2
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
        val RESTAURANT_MANAGER_PERMISSION = 2
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
        val RESTAURANT_MANAGER = 2
        if (!permissions.contains(RESTAURANT_MANAGER)) {
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
        val RESTAURANT_MANAGER = 2
        if (!permissions.contains(RESTAURANT_MANAGER)) {
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

}