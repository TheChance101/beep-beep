package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRemoteGateway

interface IGetRestaurantsUseCase {
    suspend fun getRestaurants(): List<Restaurant>
}

class GetRestaurantsUseCase(private val remoteGateway: IRemoteGateway) : IGetRestaurantsUseCase {
    override suspend fun getRestaurants(): List<Restaurant> {
        return remoteGateway.getRestaurants()
    }
}