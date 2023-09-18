package org.thechance.common.data.remote.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.*
import org.thechance.common.data.remote.mapper.toDto
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.*
import org.thechance.common.domain.entity.*
import org.thechance.common.domain.getway.IRestaurantGateway

class RestaurantGateway(private val client: HttpClient) : BaseGateway(), IRestaurantGateway {

    override suspend fun createRestaurant(restaurant: NewRestaurantInfo): Restaurant {
        return tryToExecute<ServerResponse<RestaurantDto>>(client) {
            post(urlString = "/restaurant") {
                contentType(ContentType.Application.Json)
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
        }.value?.toEntity()?: throw UnknownError()
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
        rating: Double?,
        priceLevel: String?
    ): DataWrapper<Restaurant> {

        return tryToExecute<ServerResponse<RestaurantResponse>>(client) {
            get(urlString = "/restaurants") {
                parameter("page", pageNumber)
                parameter("limit", numberOfRestaurantsInPage)
            }
        }.value?.let {
            DataWrapper(
                totalPages = it.restaurants.size.div(numberOfRestaurantsInPage),
                numberOfResult = it.total,
                result = it.restaurants.toEntity()
            )
        } ?: throw UnknownError()
    }

}

