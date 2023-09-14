package org.thechance.common.data.remote.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.*
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.CuisineDto
import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.data.remote.model.ServerResponse
import org.thechance.common.data.remote.model.UserResponse
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewRestaurantInfo
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRestaurantGateway
import org.thechance.common.presentation.restaurant.toDto

class RestaurantGateway(private val client: HttpClient) : BaseGateway(), IRestaurantGateway {

    override suspend fun createRestaurant(restaurant: NewRestaurantInfo): Restaurant {
        return tryToExecute<ServerResponse<RestaurantDto>>(client){
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

    override suspend fun getCuisines(): List<String> {
       return tryToExecute<ServerResponse<List<CuisineDto>>>(client) {
            get(urlString = "/cuisines")
        }.value?.map { it.name }?: throw UnknownError()
    }

    override suspend fun createCuisine(cuisineName: String): String {
        return tryToExecute<ServerResponse<CuisineDto>>(client) {
            submitForm(
                url = "/cuisine",
                formParameters = parameters {
                    append("name", cuisineName)
                },
            )
        }.value?.name ?: throw UnknownError()
    }

    override suspend fun deleteCuisine(cuisineName: String): String {
        return cuisineName
    }//Todo: implement endpoint deleteCuisine

    override suspend fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?
    ): DataWrapper<Restaurant> {
        return DataWrapper(10, 1, listOf())
    }//Todo: implement endpoint getRestaurants

}