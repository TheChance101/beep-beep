package org.thechance.common.data.remote.gateway

import org.thechance.common.domain.entity.AddRestaurant
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRestaurantGateway

class RestaurantGateway: BaseGateway(), IRestaurantGateway {
    override suspend fun getRestaurants(): DataWrapper<Restaurant> {
        TODO("Not yet implemented")
    }

    override suspend fun searchRestaurantsByRestaurantName(restaurantName: String): DataWrapper<Restaurant> {
        TODO("Not yet implemented")
    }

    override suspend fun filterRestaurants(
        rating: Double,
        priceLevel: Int
    ): DataWrapper<Restaurant> {
        TODO("Not yet implemented")
    }

    override suspend fun searchFilterRestaurants(
        restaurantName: String,
        rating: Double,
        priceLevel: Int
    ): DataWrapper<Restaurant> {
        TODO("Not yet implemented")
    }

    override suspend fun createRestaurant(restaurant: AddRestaurant): Restaurant {
        TODO("Not yet implemented")
    }
}