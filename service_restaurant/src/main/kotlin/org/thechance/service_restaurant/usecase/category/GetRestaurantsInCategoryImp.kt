package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway
import org.thechance.service_restaurant.entity.Restaurant

@Single
class GetRestaurantsInCategoryImp(private val restaurantGateway: RestaurantGateway) : GetRestaurantsInCategoryUseCases {
    override suspend fun invoke(categoryId: String): List<Restaurant> {
        return restaurantGateway.getRestaurantsInCategory(categoryId)
    }
}