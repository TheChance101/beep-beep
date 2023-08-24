package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRemoteGateway
import kotlin.math.floor

interface IFilterRestaurantsUseCase {
    suspend fun filterRestaurants(rating: Double, priceLevel: Int): List<Restaurant>
    suspend fun searchFilterRestaurants(
        restaurantName: String,
        rating: Double,
        priceLevel: Int
    ): List<Restaurant>
}

class FilterRestaurantsUseCase(
    private val remoteGateway: IRemoteGateway,
) : IFilterRestaurantsUseCase {
    override suspend fun filterRestaurants(rating: Double, priceLevel: Int): List<Restaurant> {
        return remoteGateway.getRestaurants().filter {
            it.priceLevel == priceLevel &&
                    when {
                        rating.rem(1) > 0.89 || rating.rem(1) == 0.0 || rating.rem(1) > 0.5
                        -> it.rating in floor(rating) - 0.1..0.49 + floor(rating)

                        else -> it.rating in 0.5 + floor(rating)..0.89 + floor(rating)
                    }
        }
    }

    override suspend fun searchFilterRestaurants(
        restaurantName: String,
        rating: Double,
        priceLevel: Int
    ): List<Restaurant> {
        return remoteGateway.searchRestaurantsByRestaurantName(restaurantName).filter {
            it.priceLevel == priceLevel &&
                    when {
                        rating.rem(1) > 0.89 || rating.rem(1) == 0.0 || rating.rem(1) > 0.5
                        -> it.rating in floor(rating) - 0.1..0.49 + floor(rating)

                        else -> it.rating in 0.5 + floor(rating)..0.89 + floor(rating)
                    }
        }
    }
}