package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.AddRestaurantToCategoryUseCases
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class AddRestaurantToCategoryUseCasesImp(private val restaurantGateway: RestaurantGateway) :
    AddRestaurantToCategoryUseCases {
    override suspend fun invoke(categoryId: String, restaurantIds: List<String>): Boolean {
        return restaurantGateway.addRestaurantsToCategory(categoryId, restaurantIds)
    }
}