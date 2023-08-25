package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRemoteGateway

interface IFilterRestaurantsUseCase {
    suspend fun invoke(rating: Double, priceLevel: Int): List<Restaurant>
}

class FilterRestaurantsUseCase(
    private val remoteGateway: IRemoteGateway,
) : IFilterRestaurantsUseCase {
    override suspend fun invoke(rating: Double, priceLevel: Int): List<Restaurant> {
        return remoteGateway.filterRestaurants(rating, priceLevel)
    }


}