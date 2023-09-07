package org.thechance.common.data.remote.gateway

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewRestaurantInfo
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRestaurantGateway

class RestaurantGateway : BaseGateway(), IRestaurantGateway {

    override suspend fun createRestaurant(restaurant: NewRestaurantInfo): Restaurant {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRestaurants(restaurant: Restaurant): Restaurant {
        TODO("Not yet implemented")
    }

    override suspend fun getCuisines(): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun createCuisine(cuisineName: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCuisine(cuisineName: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?
    ): DataWrapper<Restaurant> {
        TODO("Not yet implemented")
    }

}