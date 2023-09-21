package org.thechance.common.data.remote.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import org.thechance.common.data.remote.mapper.getPriceLevelOrNull
import org.thechance.common.data.remote.mapper.getRatingOrNull
import org.thechance.common.data.remote.mapper.toDto
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.*
import org.thechance.common.domain.entity.*
import org.thechance.common.domain.getway.IRestaurantGateway

class RestaurantGateway(private val client: HttpClient) : BaseGateway(), IRestaurantGateway {

    override suspend fun createRestaurant(restaurant: RestaurantInformation): Restaurant {
        return tryToExecute<ServerResponse<RestaurantDto>>(client) {
            post(urlString = "/restaurant") {
                setBody(restaurant.toDto())
            }
        }.value?.toEntity() ?: throw UnknownError()
    }

    override suspend fun deleteRestaurant(id: String): Boolean {
        return tryToExecute<ServerResponse<Boolean>>(client) {
            delete(urlString = "/restaurant") { url { appendPathSegments(id) } }
        }.isSuccess ?: false
    }

    override suspend fun getCuisines(): List<Cuisine> {
        return tryToExecute<ServerResponse<List<CuisineDto>>>(client) {
            get(urlString = "/cuisines")
        }.value?.toEntity() ?: throw UnknownError()
    }

    override suspend fun createCuisine(cuisineName: String): Cuisine {
        return tryToExecute<ServerResponse<CuisineDto>>(client) {
            submitForm(
                url = "/cuisine",
                formParameters = parameters {
                    append("name", cuisineName)
                },
            )
        }.value?.toEntity() ?: throw UnknownError()
    }

    override suspend fun deleteCuisine(cuisineId: String) {
        tryToExecute<ServerResponse<Boolean>>(client) {
            delete(urlString = "/cuisine") { url { appendPathSegments(cuisineId) } }
        }.isSuccess ?: false
    }

    override suspend fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double,
        priceLevel: Int,
    ): DataWrapper<Restaurant> {
        val filteredRating = getRatingOrNull(rating)
        val filteredPriceLevel = getPriceLevelOrNull(priceLevel)

        val result = tryToExecute<ServerResponse<RestaurantResponse>>(client) {
            get(urlString = "/restaurants") {
                parameter("page", pageNumber)
                parameter("limit", numberOfRestaurantsInPage)
                parameter("query", restaurantName)
                parameter("rating", filteredRating)
                parameter("priceLevel", filteredPriceLevel)
            }
        }.value

        return paginateData(
            result?.restaurants?.toEntity() ?: emptyList(),
            numberOfRestaurantsInPage,
            result?.total ?: 0
        )
    }

    override suspend fun getRestaurantById(id: String): Restaurant {
        return tryToExecute<ServerResponse<RestaurantDto>>(client) {
            get(urlString = "/restaurant") { url { appendPathSegments(id) } }
        }.value?.toEntity() ?: throw UnknownError()
    }

    override suspend fun updateRestaurant(restaurantId: String, restaurant: RestaurantInformation): Restaurant {
        return tryToExecute<ServerResponse<RestaurantDto>>(client) {
            put(urlString = "/restaurant") {
                url { appendPathSegments(restaurantId) }
                setBody(restaurant.toDto())
            }
        }.value?.toEntity() ?: throw UnknownError()
    }

}

