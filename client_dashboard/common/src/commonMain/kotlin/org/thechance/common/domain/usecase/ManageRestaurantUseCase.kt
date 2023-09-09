package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewRestaurantInfo
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRestaurantGateway


interface IManageRestaurantUseCase {

    suspend fun createRestaurant(restaurant: NewRestaurantInfo): Restaurant

    suspend fun getRestaurant(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?
    ): DataWrapper<Restaurant>

    suspend fun deleteRestaurants(restaurant: Restaurant): Restaurant

}


class ManageRestaurantUseCase(private val restaurantGateway: IRestaurantGateway) :
    IManageRestaurantUseCase {

    override suspend fun createRestaurant(restaurant: NewRestaurantInfo): Restaurant {
        return restaurantGateway.createRestaurant(restaurant)
    }

    override suspend fun getRestaurant(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?
    ): DataWrapper<Restaurant> {
        return restaurantGateway.getRestaurants(
            pageNumber,
            numberOfRestaurantsInPage,
            restaurantName,
            rating,
            priceLevel
        )
    }

    override suspend fun deleteRestaurants(restaurant: Restaurant): Restaurant {
        return restaurantGateway.deleteRestaurants(restaurant)
    }

}