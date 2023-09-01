package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.AddRestaurant
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRemoteGateway


interface IManageRestaurantUseCase {
    suspend fun createRestaurant(restaurant: AddRestaurant): Restaurant

    suspend fun getRestaurant(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?
    ): DataWrapper<Restaurant>
}


class ManageRestaurantUseCase(private val remoteGateway: IRemoteGateway) :
    IManageRestaurantUseCase {
    override suspend fun createRestaurant(restaurant: AddRestaurant): Restaurant {
        return remoteGateway.createRestaurant(restaurant)
    }

    override suspend fun getRestaurant(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?
    ): DataWrapper<Restaurant> {
        return remoteGateway.getRestaurants(pageNumber, numberOfRestaurantsInPage, restaurantName, rating, priceLevel)
    }
}