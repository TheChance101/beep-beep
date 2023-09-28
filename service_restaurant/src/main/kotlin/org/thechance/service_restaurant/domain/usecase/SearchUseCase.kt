package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.entity.RestaurantOptions
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway

interface ISearchUseCase {
    suspend fun searchRestaurant(query: String, page: Int, limit: Int): List<Restaurant>

    suspend fun searchMeal(query: String, page: Int, limit: Int): List<Meal>
}

class SearchUseCase(private val restaurantGateway: IRestaurantGateway) : ISearchUseCase {
    override suspend fun searchRestaurant(query: String, page: Int, limit: Int): List<Restaurant> {
        return restaurantGateway.getRestaurants(options = RestaurantOptions(query = query, page = page, limit = limit))
    }

    override suspend fun searchMeal(query: String, page: Int, limit: Int): List<Meal> {
        return restaurantGateway.getMeals(query = query, page = page, limit = limit)
    }


}