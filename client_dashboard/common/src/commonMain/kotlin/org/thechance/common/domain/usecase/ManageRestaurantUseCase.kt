package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.AddRestaurant
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRemoteGateway


interface IManageRestaurantUseCase {
    suspend fun createRestaurant(restaurant: AddRestaurant): Restaurant

    suspend fun getRestaurant(): List<Restaurant>
}


class ManageRestaurantUseCase(private val fakeGateway: IRemoteGateway) : IManageRestaurantUseCase {
    override suspend fun createRestaurant(restaurant: AddRestaurant): Restaurant {
        return fakeGateway.createRestaurant(restaurant)
    }

    override suspend fun getRestaurant(): List<Restaurant> {
        return fakeGateway.getRestaurants()
    }
}