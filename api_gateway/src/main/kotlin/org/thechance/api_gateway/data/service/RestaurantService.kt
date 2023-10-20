package org.thechance.api_gateway.data.service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.MealRequestDto
import org.thechance.api_gateway.data.model.PaginationResponse
import org.thechance.api_gateway.data.model.offer.OfferDto
import org.thechance.api_gateway.data.model.offer.OfferRestaurantsDto
import org.thechance.api_gateway.data.model.restaurant.*
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.data.utils.tryToExecute
import org.thechance.api_gateway.data.utils.tryToExecuteWebSocket
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
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
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
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/restaurant-permission-request") }
        )
    }

    //region Restaurant
    suspend fun getRestaurantInfo(languageCode: String, restaurantId: String): RestaurantDto {
        return client.tryToExecute(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/restaurant/$restaurantId") }
        )
    }

    @OptIn(InternalAPI::class)
    suspend fun updateRestaurant(restaurantDto: RestaurantDto, isAdmin: Boolean, languageCode: String): RestaurantDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
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

    @OptIn(InternalAPI::class)
    suspend fun getRestaurants(restaurantOptions: RestaurantOptions, languageCode: String) =
        client.tryToExecute<PaginationResponse<RestaurantDto>>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
        ) {
            post("/restaurants") {
                body = Json.encodeToString(RestaurantOptions.serializer(), restaurantOptions)
            }
        }

    @OptIn(InternalAPI::class)
    suspend fun getRestaurants(restaurantIds: List<String>, languageCode: String) =
        client.tryToExecute<List<RestaurantDto>>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
        ) {
            post("/restaurants/favorite") {
                body = Json.encodeToString(restaurantIds)
            }
        }


    suspend fun getRestaurantsByOwnerId(ownerId: String, languageCode: String): List<RestaurantDto> {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/restaurants/$ownerId") }
        )
    }

    @OptIn(InternalAPI::class)
    suspend fun addRestaurant(restaurant: RestaurantDto, languageCode: String): RestaurantDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
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
            setErrorMessage = { errorHandler.getLocalizedErrorMessage(it, languageCode) },
            method = { delete("/restaurant/$restaurantId") }
        )
    }

    //endregion

    //region meal
    suspend fun getMeal(mealId: String, languageCode: String): MealDetailsDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("meal/$mealId") }
        )
    }

    suspend fun getMealsByRestaurantId(
        restaurantId: String, page: Int, limit: Int, languageCode: String
    ): List<MealDto> {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
        ) {
            get("restaurant/$restaurantId/meals") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }
    }

    suspend fun getCuisinesMealsInRestaurant(restaurantId: String, languageCode: String) =
        client.tryToExecute<List<CuisineDetailsDto>>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
        ) {
            get("/restaurant/$restaurantId/cuisineMeals")
        }

    suspend fun getMealsByCuisineId(cuisineId: String, languageCode: String,page: Int,limit:Int): PaginationResponse<MealDto> {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/cuisine/$cuisineId/meals?page=$page&limit=$limit") }
        )
    }

    @OptIn(InternalAPI::class)
    suspend fun addMeal(mealDto: MealDto, languageCode: String): MealDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
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
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
        ) {
            put("/meal") { body = Json.encodeToString(MealDto.serializer(), mealDto) }
        }
    }

//endregion

    //region cuisine
    @OptIn(InternalAPI::class)
    suspend fun addCuisine(cuisineDto: CuisineDto, languageCode: String): CuisineDto {
        return client.tryToExecute<CuisineDto>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
        ) {
            post("/cuisine") {
                body = Json.encodeToString(CuisineDto.serializer(), cuisineDto)
            }
        }
    }

    suspend fun getCuisines(languageCode: String): List<CuisineDto> {
        return client.tryToExecute<List<CuisineDto>>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/cuisines") }
        )
    }

    suspend fun deleteCuisine(cuisineId: String, languageCode: String): Boolean {
        return client.tryToExecute<Boolean>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorHandler.getLocalizedErrorMessage(it, languageCode) },
            method = { delete("/cuisine/$cuisineId") }
        )
    }
//endregion

    //region Cart
    suspend fun getUserCart(userId: String, language: String): CartDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API, attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, language) }
        ) {
            get("/cart/$userId")
        }
    }

    suspend fun updateMealInCart(meal: MealRequestDto, language: String): CartDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API, attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, language) }
        ) {
            put("/cart/${meal.userId}") {
                parameter("restaurantId", meal.restaurantId)
                parameter("mealId", meal.mealId)
                parameter("quantity", meal.quantity)
            }
        }
    }

    suspend fun orderCart(userId: String, language: String): OrderDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API, attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, language) }
        ) {
            post("/cart/$userId/orderNow")
        }
    }

//endregion

    //region order
    @OptIn(InternalAPI::class)
    suspend fun createOrder(order: OrderDto, languageCode: String): OrderDto {
        return client.tryToExecute<OrderDto>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
        ) {
            post("/order") {
                body = Json.encodeToString(OrderDto.serializer(), order)
            }
        }
    }

    suspend fun updateOrderStatus(orderId: String, status: Int, languageCode: String): OrderDto {
        return client.tryToExecute<OrderDto>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { put("/order/$orderId?status=$status") }
        )
    }

    suspend fun getOrdersHistoryInRestaurant(
        restaurantId: String, page: Int, limit: Int, languageCode: String
    ): PaginationResponse<OrderDto> {
        return client.tryToExecute<PaginationResponse<OrderDto>>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/order/restaurant/$restaurantId/history?page=$page&&limit=$limit") }
        )
    }

    suspend fun getOrdersHistoryForUser(
        userId: String, page: Int, limit: Int, languageCode: String
    ): PaginationResponse<OrderDto> {
        return client.tryToExecute<PaginationResponse<OrderDto>>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/order/user/$userId/history?page=$page&&limit=$limit") }
        )
    }

    suspend fun getOrdersCountByDaysBefore(
        restaurantId: String, daysBack: Int, languageCode: String
    ): List<Map<Int, Int>> {
        return client.tryToExecute<List<Map<Int, Int>>>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/order/count-by-days-back?restaurantId=$restaurantId&&daysBack=$daysBack") }
        )
    }

    suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String, daysBack: Int, languageCode: String
    ): List<Map<Int, Double>> {
        return client.tryToExecute<List<Map<Int, Double>>>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/order/revenue-by-days-back?restaurantId=$restaurantId&&daysBack=$daysBack") }
        )
    }

    suspend fun getIncomingOrders(restaurantId: String): Flow<OrderDto> {
        return client.tryToExecuteWebSocket<OrderDto>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            path = "/order/restaurant/$restaurantId",
        )
    }

    suspend fun trackOrder(orderId: String): Flow<OrderDto> {
        return client.tryToExecuteWebSocket<OrderDto>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            path = "/order/track/$orderId",
        )
    }

    suspend fun getActiveOrders(restaurantId: String, languageCode: String): List<OrderDto> {
        return client.tryToExecute<List<OrderDto>>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/order/$restaurantId/orders") }
        )
    }

    suspend fun deleteRestaurantByOwnerId(id: String): Boolean {
        return client.tryToExecute<Boolean>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            method = { delete("/restaurant/owner/$id") }
        )
    }

    suspend fun search(query: String?, page: String?, limit: String?, languageCode: String): ExploreRestaurantDto {
        return client.tryToExecute(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            get("/restaurants/search") {
                parameter("query", query)
                parameter("page", page)
                parameter("limit", limit)
            }
        }
    }

    suspend fun isRestaurantExisted(restaurantId: String?, language: String): Boolean {
        return client.tryToExecute<Boolean>(
            api = APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, language) },
            method = { get("/restaurant/isExisted/$restaurantId") }
        )
    }
//endregion


    //region offer
    suspend fun addOffer(offerTitle: String, languageCode: String) = client.tryToExecute<OfferDto>(
        APIs.RESTAURANT_API,
        attributes = attributes,
        setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
    ) {
        post("/category") {
            formData {
                parameter("categoryName", offerTitle)
            }
        }
    }

    //TODO:NEED TO CHANGE RETURN TYP
    @OptIn(InternalAPI::class)
    suspend fun addRestaurantsToOffer(restaurantIds: List<String>, offerId: String, languageCode: String) =
        client.tryToExecute<Boolean>(
            APIs.RESTAURANT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
        ) {
            post("/category/$offerId/restaurants") {
                body = Json.encodeToString(ListSerializer(String.serializer()), restaurantIds)
            }
        }

    suspend fun getOffers(languageCode: String) = client.tryToExecute<List<OfferDto>>(
        APIs.RESTAURANT_API,
        attributes = attributes,
        setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
    ) { get("/categories") }


    suspend fun getOffersWithRestaurants(languageCode: String) = client.tryToExecute<List<OfferRestaurantsDto>>(
        APIs.RESTAURANT_API,
        attributes = attributes,
        setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
    ) { get("/categories/restaurants") }
//endregion
}