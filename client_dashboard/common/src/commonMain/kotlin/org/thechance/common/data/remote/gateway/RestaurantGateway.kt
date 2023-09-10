package org.thechance.common.data.remote.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.CuisineDto
import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.data.remote.model.ServerResponse
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

    override suspend fun deleteRestaurants(restaurant: Restaurant): Restaurant {
        return Restaurant(
            id = "1",
            name = restaurant.name,
            ownerUsername = restaurant.ownerUsername,
            phoneNumber = restaurant.phoneNumber,
            rating = "4.5".toDouble(),
            priceLevel = 4,
            workingHours = restaurant.workingHours
        )
    }

    override suspend fun getCuisines(): List<String> {
        return listOf("Italian", "Chinese", "Mexican", "American", "Indian", "Japanese", "Thai")
    }

    override suspend fun createCuisine(cuisineName: String): String {
        println(cuisineName)
        return tryToExecute<ServerResponse<CuisineDto>>(client) {
            submitForm(
                url = "/cuisine",
                formParameters = parameters {
                    append("name", cuisineName)
                },
            )
        }.value?.name ?: ""
    }

    override suspend fun deleteCuisine(cuisineName: String): String {
        return ""
    }

    override suspend fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?
    ): DataWrapper<Restaurant> {
        return DataWrapper(10, 1, listOf())
    }

}