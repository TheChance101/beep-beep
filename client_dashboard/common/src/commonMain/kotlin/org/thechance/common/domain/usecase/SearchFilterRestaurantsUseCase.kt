package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRemoteGateway

interface ISearchFilterRestaurantsUseCase {
    suspend operator fun invoke(
        restaurantName: String,
        rating: Double,
        priceLevel: Int
    ): List<Restaurant>
}

class SearchFilterRestaurantsUseCase(
    private val remoteGateway: IRemoteGateway,
) : ISearchFilterRestaurantsUseCase {
    override suspend operator fun invoke(
        restaurantName: String,
        rating: Double,
        priceLevel: Int
    ): List<Restaurant> {
        return remoteGateway.searchFilterRestaurants(restaurantName, rating, priceLevel)
    }
}