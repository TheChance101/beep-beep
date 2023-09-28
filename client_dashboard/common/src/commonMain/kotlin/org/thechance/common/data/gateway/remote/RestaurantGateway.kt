package org.thechance.common.data.gateway.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import org.thechance.common.data.gateway.remote.model.CuisineDto
import org.thechance.common.data.gateway.remote.model.RestaurantDto
import org.thechance.common.data.gateway.remote.model.RestaurantResponse
import org.thechance.common.data.gateway.remote.model.ServerResponse
import org.thechance.common.data.gateway.remote.mapper.getPriceLevelOrNull
import org.thechance.common.data.gateway.remote.mapper.getRatingOrNull
import org.thechance.common.data.gateway.remote.mapper.toDto
import org.thechance.common.data.gateway.remote.mapper.toEntity
import org.thechance.common.domain.entity.*
import org.thechance.common.domain.getway.IRestaurantGateway

class RestaurantGateway(private val client: HttpClient) : BaseGateway(), IRestaurantGateway {

    override suspend fun createRestaurant(restaurant: NewRestaurantInfo): Restaurant {
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

}

