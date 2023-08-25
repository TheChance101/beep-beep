package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.AddRestaurant
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRemoteGateway

interface ICreateNewRestaurantUseCase {

    suspend fun createRestaurant(restaurant: AddRestaurant): Restaurant

}

class CreateNewRestaurantUseCase(private val remoteGateway: IRemoteGateway) :
    ICreateNewRestaurantUseCase {
    override suspend fun createRestaurant(restaurant: AddRestaurant): Restaurant {
        return remoteGateway.createRestaurant(restaurant)
    }
}