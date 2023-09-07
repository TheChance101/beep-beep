package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewRestaurantInfo
import org.thechance.common.domain.entity.Restaurant


interface IRemoteGateway {

    suspend fun getUserData(): String

    suspend fun getPdfTaxiReport()

    suspend fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?
    ): DataWrapper<Restaurant>

    suspend fun createRestaurant(restaurant: NewRestaurantInfo): Restaurant

    suspend fun getCurrentLocation(): String

    suspend fun loginUser(username: String, password: String): Pair<String, String>

    suspend fun deleteRestaurants(restaurant: Restaurant): Restaurant

    suspend fun getCuisines(): List<String>

    suspend fun createCuisine(cuisineName: String): String?

    suspend fun deleteCuisine(cuisineName: String): String

}