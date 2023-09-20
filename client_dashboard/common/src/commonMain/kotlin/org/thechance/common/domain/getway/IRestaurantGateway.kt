package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.Cuisine
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewRestaurantInfo
import org.thechance.common.domain.entity.Restaurant

interface IRestaurantGateway {

    suspend fun createRestaurant(restaurant: NewRestaurantInfo): Restaurant

    suspend fun deleteRestaurant(id: String): Boolean

    suspend fun getCuisines(): List<Cuisine>

    suspend fun createCuisine(cuisineName: String): Cuisine

    suspend fun deleteCuisine(cuisineId: String)

    suspend fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double,
        priceLevel: Int,
    ): DataWrapper<Restaurant>

}