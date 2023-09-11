package org.thechance.common.data.remote.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.thechance.common.data.remote.mapper.toDto
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.data.remote.model.ServerResponse
import org.thechance.common.data.remote.model.TaxiDto
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewRestaurantInfo
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.Time
import org.thechance.common.domain.getway.IRestaurantGateway
import org.thechance.common.presentation.restaurant.toDto

class RestaurantGateway(private val client: HttpClient):BaseGateway(), IRestaurantGateway {
    override suspend fun createRestaurant(restaurant: NewRestaurantInfo): Restaurant {
        val result = tryToExecute<ServerResponse<RestaurantDto>>(client) {
            post(urlString = "/restaurant") {
                contentType(ContentType.Application.Json)
                setBody(restaurant.toDto())
            }
        }.value
        return result?.toEntity() ?: throw UnknownError()
    }

    override suspend fun deleteRestaurants(restaurant: Restaurant): Restaurant {
        return Restaurant(
            id = "",
            name = "Test",
            ownerUsername = "hassan",
            phoneNumber = "212145654",
            rating = 5.0,
            priceLevel = 1,
            workingHours = Pair(Time(1,15),Time(1,15))
        )
    }

    override suspend fun getCuisines(): List<String> {
        return emptyList()/*TODO("Not yet implemented")*/
    }

    override suspend fun createCuisine(cuisineName: String): String {
        return ""/*TODO("Not yet implemented")*/
    }

    override suspend fun deleteCuisine(cuisineName: String): String {
        return ""/*TODO("Not yet implemented")*/
    }

    override suspend fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?
    ): DataWrapper<Restaurant> {
        /*TODO("Not yet implemented")*/
        return DataWrapper(
            totalPages = 1,
            numberOfResult = 1,
            result = listOf(
                Restaurant(
                    id = "",
                    name = "",
                    ownerUsername = "",
                    phoneNumber = "",
                    rating = 5.0,
                    priceLevel = 1,
                    workingHours = Pair(Time(1,15),Time(1,15))
                )
            )
        )
    }
}