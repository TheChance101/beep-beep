package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRemoteGateway

interface ISearchRestaurantsByNameUseCase {
    suspend operator fun invoke(restaurantName: String): List<Restaurant>
}

class SearchRestaurantsByNameUseCase(
    private val remoteGateway: IRemoteGateway,
) : ISearchRestaurantsByNameUseCase {

    override suspend operator fun invoke(restaurantName: String): List<Restaurant> {
        return if (restaurantName.isEmpty()) remoteGateway.getRestaurants()
        else remoteGateway.searchRestaurantsByRestaurantName(restaurantName)
    }

}