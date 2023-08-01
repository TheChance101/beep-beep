package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.GetRestaurantsInCategoryUseCases
import org.thechance.service_restaurant.entity.Restaurant
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class GetRestaurantsInCategoryImp(private val restaurantGateway: RestaurantGateway) : GetRestaurantsInCategoryUseCases {
    override suspend fun invoke(categoryId: String): List<Restaurant> {
        return restaurantGateway.getRestaurantsInCategory(categoryId)
    }
}